package com.github.ericsouza;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fellbaum.jemoji.Emoji;
import net.fellbaum.jemoji.EmojiManager;
import net.fellbaum.jemoji.Emojis;

public class EmojiTest {

    // Expressão regular para capturar os códigos HTML &#xHEX; ou &#DEC;
    private static final Pattern HTML_HEXADECIMAL_CODE_PATTERN = Pattern.compile("&#(x?[0-9A-Fa-f]+);");
    // Expressão regular para capturar o formato ::HEX::
    private static final Pattern DB_EMOJI_PATTERN = Pattern.compile("::([0-9A-Fa-f]+)::");

    public static void main(String[] args) {
        Emoji fire = Emojis.FIRE;
        Emoji rocket = Emojis.ROCKET;

        //String fraseOriginal = "Olá, hoje o " + rocket.getEmoji() + " vai pegar " + fire.getEmoji();
        String fraseOriginal = "emoji: 👨🏻‍👩🏼‍👧🏽‍👦🏾";
        String htmlHex = EmojiManager.replaceAllEmojis(fraseOriginal, Emoji::getHtmlHexadecimalCode);
        String hexDb = convertHtmlEntityToHex(htmlHex);
        String outpuString = convertHexToEmoji(hexDb);
        System.out.println(fraseOriginal);
        System.out.println(htmlHex);
        System.out.println(hexDb);
        System.out.println(outpuString);

    }

    public static String convertHtmlEntityToHex(String input) {
        Matcher matcher = HTML_HEXADECIMAL_CODE_PATTERN.matcher(input);

        // StringBuilder para construir o resultado
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            // Captura o código hexadecimal ou decimal (sem os &# e ;)
            String code = matcher.group(1);
            int codePoint;

            // Verifica se o código é hexadecimal (inicia com "x" ou "X")
            if (code.startsWith("x") || code.startsWith("X")) {
                // Converte de hexadecimal para inteiro
                codePoint = Integer.parseInt(code.substring(1), 16);
            } else {
                // Converte de decimal para inteiro
                codePoint = Integer.parseInt(code, 10);
            }

            // Converte o ponto de código para o formato desejado ::HEX::
            String formattedCode = Integer.toHexString(codePoint).toUpperCase();
            matcher.appendReplacement(result, "::" + formattedCode + "::");
        }

        // Adiciona o restante da string
        matcher.appendTail(result);

        return result.toString();
    }

    public static String convertHexToEmoji(String input) {
        Matcher matcher = DB_EMOJI_PATTERN.matcher(input);

        // StringBuilder para construir o resultado
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            // Captura o código hexadecimal (sem os "::")
            String hexCode = matcher.group(1);

            try {
                // Converte o código hexadecimal para um ponto de código Unicode
                int codePoint = Integer.parseInt(hexCode, 16);

                // Converte o ponto de código para o emoji correspondente
                String emoji = new String(Character.toChars(codePoint));

                // Substitui o código pelo emoji
                matcher.appendReplacement(result, emoji);
            } catch (IllegalArgumentException e) {
                // Se houver erro, mantém o código original
                matcher.appendReplacement(result, matcher.group(0));
            }
        }

        // Adiciona o restante da string
        matcher.appendTail(result);

        return result.toString();
    }

}
