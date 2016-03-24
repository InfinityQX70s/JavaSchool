package com.jschool.utils;

import org.springframework.stereotype.Component;

import java.net.*;
import java.io.*;
import java.lang.Math;

/**
 * Created by infinity on 20.03.16.
 */
@Component
public class SmsUtil {
    String SMSC_LOGIN = "mazumisha";     // логин клиента
    String SMSC_PASSWORD = "de157ecb35aa035a255a6121feb4cc55";  // пароль или MD5-хеш пароля в нижнем регистре
    boolean SMSC_HTTPS = false;         // использовать HTTPS протокол
    String SMSC_CHARSET = "utf-8";       // кодировка сообщения: koi8-r, windows-1251 или utf-8 (по умолчанию)
    boolean SMSC_DEBUG = false;         // флаг отладки
    boolean SMSC_POST = false;         // Использовать метод POST

    /**
     * constructors
     */
    public SmsUtil() {
    }

    public SmsUtil(String login, String password) {
        SMSC_LOGIN = login;
        SMSC_PASSWORD = password;
    }

    public SmsUtil(String login, String password, String charset) {
        SMSC_LOGIN = login;
        SMSC_PASSWORD = password;
        SMSC_CHARSET = charset;
    }

    public SmsUtil(String login, String password, String charset, boolean debug) {
        SMSC_LOGIN = login;
        SMSC_PASSWORD = password;
        SMSC_CHARSET = charset;
        SMSC_DEBUG = debug;
    }

    /**
     * Отправка SMS
     *
     * @param phones  - список телефонов через запятую или точку с запятой
     * @param message - отправляемое сообщение
     * @return array (<id>, <количество sms>, <стоимость>, <баланс>) в случае успешной отправки
     * или массив (<id>, -<код ошибки>) в случае ошибки
     */

    public String[] sendSms(String phones, String message) throws IOException, InterruptedException {
        int translit = 1;
        String id = "";
        String[] m = {};
        m = _smsc_send_cmd("send", "cost=3&phones=" + URLEncoder.encode(phones, SMSC_CHARSET)
                + "&mes=" + URLEncoder.encode(message, SMSC_CHARSET)
                + "&translit=" + translit + "&id=" + id);
        if (SMSC_DEBUG) {
            if (Integer.parseInt(m[1]) > 0) {
                System.out.println("Сообщение отправлено успешно. ID: " + m[0] + ", всего SMS: " + m[1] + ", стоимость: " + m[2] + ", баланс: " + m[3]);
            } else {
                System.out.print("Ошибка №" + Math.abs(Integer.parseInt(m[1])));
                System.out.println(Integer.parseInt(m[0]) > 0 ? (", ID: " + m[0]) : "");
            }
        }
        return m;
    }


    /**
     * Получение стоимости SMS
     *
     * @param phones   - список телефонов через запятую или точку с запятой
     * @param message  - отправляемое сообщение.
     * @param translit - переводить или нет в транслит (1,2 или 0)
     * @param format   - формат сообщения (0 - обычное sms, 1 - flash-sms, 2 - wap-push, 3 - hlr, 4 - bin, 5 - bin-hex, 6 - ping-sms)
     * @param sender   - имя отправителя (Sender ID)
     * @param query    - строка дополнительных параметров, добавляемая в URL-запрос ("list=79999999999:Ваш пароль: 123\n78888888888:Ваш пароль: 456")
     * @return array(<стоимость>, <количество sms>) либо (0, -<код ошибки>) в случае ошибки
     */

    public String[] get_sms_cost(String phones, String message, int translit, int format, String sender, String query) throws IOException, InterruptedException {
        String[] formats = {"", "flash=1", "push=1", "hlr=1", "bin=1", "bin=2", "ping=1"};
        String[] m = {};
        m = _smsc_send_cmd("send", "cost=1&phones=" + URLEncoder.encode(phones, SMSC_CHARSET)
                + "&mes=" + URLEncoder.encode(message, SMSC_CHARSET)
                + "&translit=" + translit + (format > 0 ? "&" + formats[format] : "")
                + (sender == "" ? "" : "&sender=" + URLEncoder.encode(sender, SMSC_CHARSET))
                + (query == "" ? "" : "&" + query));
        // (cost, cnt) или (0, -error)
        if (SMSC_DEBUG) {
            if (Integer.parseInt(m[1]) > 0) {
                System.out.println("Стоимость рассылки: " + m[0] + ", Всего SMS: " + m[1]);
            } else {
                System.out.print("Ошибка №" + Math.abs(Integer.parseInt(m[1])));
            }
        }
        return m;
    }

    /**
     * Проверка статуса отправленного SMS или HLR-запроса
     *
     * @param id    - ID cообщения
     * @param phone - номер телефона
     * @param all   - дополнительно возвращаются элементы в конце массива:
     *              (<время отправки>, <номер телефона>, <стоимость>, <sender id>, <название статуса>, <текст сообщения>)
     * @return array
     * для отправленного SMS (<статус>, <время изменения>, <код ошибки sms>)
     * для HLR-запроса (<статус>, <время изменения>, <код ошибки sms>, <код страны регистрации>, <код оператора абонента>,
     * <название страны регистрации>, <название оператора абонента>, <название роуминговой страны>, <название роумингового оператор
     * <код IMSI SIM-карты>, <номер сервис-центра>)
     * либо array(0, -<код ошибки>) в случае ошибки
     */

    public String[] get_status(int id, String phone, int all) throws IOException, InterruptedException {
        String[] m = {};
        String tmp;
        m = _smsc_send_cmd("status", "phone=" + URLEncoder.encode(phone, SMSC_CHARSET) + "&id=" + id + "&all=" + all);
        if (SMSC_DEBUG) {
            if (m[1] != "" && Integer.parseInt(m[1]) >= 0) {
                java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Integer.parseInt(m[1]));
                System.out.println("Статус SMS = " + m[0]);
            } else
                System.out.println("Ошибка №" + Math.abs(Integer.parseInt(m[1])));
        }
        if (all == 1 && m.length > 9 && (m.length < 14 || m[14] != "HLR")) {
            tmp = _implode(m, ",");
            m = tmp.split(",", 9);
        }
        return m;
    }

    /**
     * Получения баланса
     *
     * @return String баланс или пустую строку в случае ошибки
     */

    public String get_balance() throws IOException, InterruptedException {
        String[] m = {};
        m = _smsc_send_cmd("balance", ""); // (balance) или (0, -error)
        if (SMSC_DEBUG) {
            if (m.length == 1)
                System.out.println("Сумма на счете: " + m[0]);
            else
                System.out.println("Ошибка №" + Math.abs(Integer.parseInt(m[1])));
        }
        return m.length == 2 ? "" : m[0];
    }

    /**
     * Формирование и отправка запроса
     *
     * @param cmd - требуемая команда
     * @param arg - дополнительные параметры
     */

    private String[] _smsc_send_cmd(String cmd, String arg) throws IOException, InterruptedException {
        String[] m = {};
        String ret = ",";
        String url = (SMSC_HTTPS ? "https" : "http") + "://smsc.ru/sys/" + cmd + ".php?login=" + URLEncoder.encode(SMSC_LOGIN, SMSC_CHARSET)
                + "&psw=" + URLEncoder.encode(SMSC_PASSWORD, SMSC_CHARSET)
                + "&fmt=1&charset=" + SMSC_CHARSET + "&" + arg;
        int i = 0;
        do {
            if (i > 0)
                Thread.sleep(2000 + 1000 * i);
            if (i == 2)
                url = url.replace("://smsc.ru/", "://www2.smsc.ru/");
            ret = _smsc_read_url(url);
        }
        while (ret == "" && ++i < 4);
        return ret.split(",");
    }

    /**
     * Чтение URL
     *
     * @param url - ID cообщения
     * @return line - ответ сервера
     */
    private String _smsc_read_url(String url) throws IOException {
        String line = "", real_url = url;
        String[] param = {};
        boolean is_post = (SMSC_POST || url.length() > 2000);
        if (is_post) {
            param = url.split("\\?", 2);
            real_url = param[0];
        }
        URL u = new URL(real_url);
        InputStream is;

        if (is_post) {
            URLConnection conn = u.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream(), SMSC_CHARSET);
            os.write(param[1]);
            os.flush();
            os.close();
            System.out.println("post");
            is = conn.getInputStream();
        } else {
            is = u.openStream();
        }
        InputStreamReader reader = new InputStreamReader(is, SMSC_CHARSET);
        int ch;
        while ((ch = reader.read()) != -1) {
            line += (char) ch;
        }
        reader.close();
        return line;
    }

    private static String _implode(String[] ary, String delim) {
        String out = "";
        for (int i = 0; i < ary.length; i++) {
            if (i != 0)
                out += delim;
            out += ary[i];
        }
        return out;
    }
}
