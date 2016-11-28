package com.sortedqueue.programmercreek.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import prettify.PrettifyParser;
import syntaxhighlight.ParseResult;
import syntaxhighlight.Parser;

public class PrettifyHighlighter {
    //private static final Map<String, String> COLORS = buildColorsMap_Dark_BG();
    private static final Map<String, String> COLORS = buildColorsMap_White_BG();

    private static final String FONT_PATTERN = "<font color=\"#%s\">%s</font>";

    private final Parser parser = new PrettifyParser();
    
    static PrettifyHighlighter mInstance;
    public static PrettifyHighlighter getInstance() {
    	if( mInstance == null ) {
    		mInstance = new PrettifyHighlighter();
    	}
    	return mInstance;
    }

    public String highlight(String fileExtension, String sourceCode) {
        StringBuilder highlighted = new StringBuilder();
        List<ParseResult> results = parser.parse(fileExtension, sourceCode);
        for(ParseResult result : results){
            String type = result.getStyleKeys().get(0);
            String content = sourceCode.substring(result.getOffset(), result.getOffset() + result.getLength());
            highlighted.append(String.format(FONT_PATTERN, getColor(type), content));
        }
        return highlighted.toString();
    }

    private String getColor(String type){
        return COLORS.containsKey(type) ? COLORS.get(type) : COLORS.get("pln");
    }

    @SuppressWarnings("unused")
	private static Map<String, String> buildColorsMap_Dark_BG() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("typ", "87cefa"); //#517C96
        map.put("kwd", "00ff00"); //#006600
        map.put("lit", "ffff00"); //#6B6B00
        map.put("com", "999999"); //#5C5C5C
        map.put("str", "ff4500"); //#ff4500
        map.put("pun", "eeeeee"); //#8F8F8F
        map.put("pln", "ffffff"); //#000000
        return map;
    }
    
    private static Map<String, String> buildColorsMap_White_BG() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("typ", "517C96"); //#517C96
        map.put("kwd", "006600"); //#006600
        map.put("lit", "006699"); //#6B6B00
        map.put("com", "6B6B00"); //#5C5C5C
        map.put("str", "ff4500"); //#ff4500
        map.put("pun", "ff4500"); //#8F8F8F
        map.put("pln", "000000"); //#000000
        return map;
    }
}