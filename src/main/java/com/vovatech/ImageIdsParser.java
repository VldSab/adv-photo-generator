package com.vovatech;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@UtilityClass
public class ImageIdsParser {
    /*
     *Захватываем только число после id=
     *Пример: ![🔤](tg://emoji?id=5463032576119679082)
     */
    private static final Pattern TG_IMG_ID =
            Pattern.compile("!\\[[^\\]]*]\\(<?tg://emoji\\?id=(\\d+)>?\\)");

    public static List<String> parseIds(@NonNull String text) {
        List<String> ids = new ArrayList<>();
        Matcher m = TG_IMG_ID.matcher(text);
        while (m.find()) {
            ids.add(m.group(1));
        }
        if (ids.isEmpty()) {
            log.error("ID tg://emoji в тексте не найдены");
        }
        return ids;
    }
}
