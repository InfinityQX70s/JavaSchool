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
    String one = "mazumisha";     // логин клиента
    String two = "de157ecb35aa035a255a6121feb4cc55";  // пароль или MD5-хеш пароля в нижнем регистре
    String smsCharset = "utf-8";       // кодировка сообщения: koi8-r, windows-1251 или utf-8 (по умолчанию)

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
        String[] m = _smsc_send_cmd("send", "cost=3&phones=" + URLEncoder.encode(phones, smsCharset)
                + "&mes=" + URLEncoder.encode(message, smsCharset)
                + "&translit=" + translit + "&id=" + id);
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
        String[] m = _smsc_send_cmd("send", "cost=1&phones=" + URLEncoder.encode(phones, smsCharset)
                + "&mes=" + URLEncoder.encode(message, smsCharset)
                + "&translit=" + translit + (format > 0 ? "&" + formats[format] : "")
                + (sender == "" ? "" : "&sender=" + URLEncoder.encode(sender, smsCharset))
                + (query == "" ? "" : "&" + query));
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
        m = _smsc_send_cmd("status", "phone=" + URLEncoder.encode(phone, smsCharset) + "&id=" + id + "&all=" + all);
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
        String url = "http" + "://smsc.ru/sys/" + cmd + ".php?login=" + URLEncoder.encode(one, smsCharset)
                + "&psw=" + URLEncoder.encode(two, smsCharset)
                + "&fmt=1&charset=" + smsCharset + "&" + arg;
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
        URL u = new URL(real_url);
        InputStream is;
        is = u.openStream();
        InputStreamReader reader = new InputStreamReader(is, smsCharset);
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
