package com.sortedqueue.programmercreek.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ReplacementSpan;
import android.util.AttributeSet;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alok on 14/09/17.
 */

public class CodeEditor extends AppCompatEditText {

    public interface OnTextChangedListener {
        void onTextChanged(String text);
    }

    private static final Pattern PATTERN_LINE = Pattern.compile(
            ".*\\n");
    private static final Pattern PATTERN_NUMBERS = Pattern.compile(
            "\\b(\\d*[.]?\\d+)\\b");
    private static final Pattern PATTERN_PREPROCESSOR = Pattern.compile(
            "^[\t ]*(#define|#undef|#if|#ifdef|#ifndef|#else|#elif|#endif|" +
                    "#error|#pragma|#extension|#version|#line|#include)\\b",
            Pattern.MULTILINE);
    private static final Pattern PATTERN_KEYWORDS = Pattern.compile(
            "\\b(attribute|uniform|varying|inout|" +
                    "lowp|mediump|highp|precision|invariant|discard|mat2|mat3|" +
                    "mat4|vec2|vec3|vec4|ivec2|ivec3|ivec4|bvec2|bvec3|bvec4|sampler2D|" +
                    "samplerCube|gl_Vertex|gl_FragCoord|gl_FragColor|" +
                    "alignof|align_union|asm|axiom|bool|concept|concept_map|const_cast|" +
                    "constexpr|decltype|dynamic_cast|explicit|friend|generic|late_check|mutable|namespace|" +
                    "nullptr|property|reinterpret_cast|static_assert|static_cast|template|typeid|typename|using|" +
                    "abstract|boolean|byte|extends|final|implements|instanceof|interface|native|strictfp|" +
                    "synchronized|throws|transient|base|by|checked|decimal|delegate|descending|dynamic|event|fixed|group|implicit|" +
                    "internal|into|let|lock|object|out|override|orderby|params|partial|readonly|ref|sbyte|sealed|" +
                    "stackalloc|string|select|uint|ulong|unchecked|unsafe|ushort|virtual|where|auto|char|const|default|" +
                    "double|enum|extern|float|inline|int|long|register|short|signed|sizeof|static|struct|switch|typedef|union|" +
                    "unsigned|void|volatile|catch|new|operator|private|protected|public|this|throw|typeof|debugger" +
                    "|export|get|null|undefined|var|Infinity|NaN|caller|delete|die|dump|exit|foreach|goto|last|my|no|our|package|" +
                    "require|sub|use|wantarray|as|assert|del|except|exec|finally|from|global|import|is|lambda|nonlocal|" +
                    "pass|print|raise|try|with|False|True|None|alias|and|begin|class|def|defined|elsif|end|ensure|false|module|next|nil|not|or|redo" +
                    "|rescue|retry|self|super|true|undef|unless|when|yield|BEGIN|END|break|continue|do" +
                    "|else|for|if|return|while|case|done|elif|esac|eval|fi|function|in|local|set|then|until" +
                    "|printf|scanf|gets|puts|println|getchar|putchar|getch|exception|perror|cin|cout)\\b");
    private static final Pattern PATTERN_BUILTINS = Pattern.compile(
            "\\b(radians|degrees|sin|cos|tan|asin|acos|atan|pow|" +
                    "exp|log|exp2|log2|sqrt|inversesqrt|abs|sign|floor|ceil|fract|mod|" +
                    "min|max|clamp|mix|step|smoothstep|length|distance|dot|cross|" +
                    "normalize|faceforward|reflect|refract|matrixCompMult|lessThan|" +
                    "lessThanEqual|greaterThan|greaterThanEqual|equal|notEqual|any|all|" +
                    "not|dFdx|dFdy|fwidth|texture2D|texture2DProj|texture2DLod|" +
                    "texture2DProjLod|textureCube|textureCubeLod)\\b");
    private static final Pattern PATTERN_COMMENTS = Pattern.compile(
            "/\\*(?:.|[\\n\\r])*?\\*/|//.*");
    private static final Pattern PATTERN_TRAILING_WHITE_SPACE = Pattern.compile(
            "[\\t ]+$",
            Pattern.MULTILINE);
    private static final Pattern PATTERN_INSERT_UNIFORM = Pattern.compile(
            "^([ \t]*uniform.+)$",
            Pattern.MULTILINE);
    private static final Pattern PATTERN_ENDIF = Pattern.compile(
            "(#endif)\\b");

    private final Handler updateHandler = new Handler();
    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            Editable e = getText();

            if (onTextChangedListener != null) {
                onTextChangedListener.onTextChanged(
                        e.toString());
            }

            highlightWithoutChange(e);
        }
    };

    private OnTextChangedListener onTextChangedListener;
    private int updateDelay = 1000;
    private int errorLine = 0;
    private boolean dirty = false;
    private boolean modified = true;
    private int colorError;
    private int colorNumber;
    private int colorKeyword;
    private int colorBuiltin;
    private int colorComment;
    private int colorPunctuation;
    private int tabWidthInCharacters = 0;
    private int tabWidth = 0;

    public CodeEditor(Context context) {
        super(context);
        init(context);
    }

    public CodeEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CodeEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setOnTextChangedListener(OnTextChangedListener listener) {
        onTextChangedListener = listener;
    }

    public void setUpdateDelay(int ms) {
        updateDelay = ms;
    }

    public void setTabWidth(int characters) {
        if (tabWidthInCharacters == characters) {
            return;
        }

        tabWidthInCharacters = characters;
        tabWidth = Math.round(getPaint().measureText("m") * characters);
    }

    public boolean hasErrorLine() {
        return errorLine > 0;
    }

    public void setErrorLine(int line) {
        errorLine = line;
    }

    public void updateHighlighting() {
        highlightWithoutChange(getText());
    }

    public boolean isModified() {
        return dirty;
    }

    public void setTextHighlighted(CharSequence text) {
        if (text == null) {
            text = "";
        }

        cancelUpdate();

        errorLine = 0;
        dirty = false;

        modified = false;
        setText(highlight(new SpannableStringBuilder(text)));
        modified = true;

        if (onTextChangedListener != null) {
            onTextChangedListener.onTextChanged(text.toString());
        }
    }

    public String getCleanText() {
        return PATTERN_TRAILING_WHITE_SPACE
                .matcher(getText())
                .replaceAll("");
    }

    public void insertTab() {
        int start = getSelectionStart();
        int end = getSelectionEnd();

        getText().replace(
                Math.min(start, end),
                Math.max(start, end),
                "\t",
                0,
                1);
    }

    public void addUniform(String statement) {
        if (statement == null) {
            return;
        }

        Editable e = getText();
        removeUniform(e, statement);

        Matcher m = PATTERN_INSERT_UNIFORM.matcher(e);
        int start = -1;

        while (m.find()) {
            start = m.end();
        }

        if (start > -1) {
            // add line break before statement because it's
            // inserted before the last line-break
            statement = "\n" + statement;
        } else {
            // add a line break after statement if there's no
            // uniform already
            statement += "\n";

            // add an empty line between the last #endif
            // and the now following uniform
            if ((start = endIndexOfLastEndIf(e)) > -1) {
                statement = "\n" + statement;
            }

            // move index past line break or to the start
            // of the text when no #endif was found
            ++start;
        }

        e.insert(start, statement);
    }

    private void removeUniform(Editable e, String statement) {
        if (statement == null) {
            return;
        }

        String regex = "^(" + statement.replace(" ", "[ \\t]+");
        int p = regex.indexOf(";");
        if (p > -1) {
            regex = regex.substring(0, p);
        }
        regex += ".*\\n)$";

        Matcher m = Pattern.compile(regex, Pattern.MULTILINE).matcher(e);
        if (m.find()) {
            e.delete(m.start(), m.end());
        }
    }

    private int endIndexOfLastEndIf(Editable e) {
        Matcher m = PATTERN_ENDIF.matcher(e);
        int idx = -1;

        while (m.find()) {
            idx = m.end();
        }

        return idx;
    }

    private void init(Context context) {
        setHorizontallyScrolling(true);

        setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(
                    CharSequence source,
                    int start,
                    int end,
                    Spanned dest,
                    int dstart,
                    int dend) {
                if (modified &&
                        end - start == 1 &&
                        start < source.length() &&
                        dstart < dest.length()) {
                    char c = source.charAt(start);

                    if (c == '\n') {
                        return autoIndent(source, dest, dstart, dend);
                    }
                }

                return source;
            }
        }});

        addTextChangedListener(new TextWatcher() {
            private int start = 0;
            private int count = 0;

            @Override
            public void onTextChanged(
                    CharSequence s,
                    int start,
                    int before,
                    int count) {
                this.start = start;
                this.count = count;
            }

            @Override
            public void beforeTextChanged(
                    CharSequence s,
                    int start,
                    int count,
                    int after) {
            }

            @Override
            public void afterTextChanged(Editable e) {
                cancelUpdate();
                convertTabs(e, start, count);

                if (!modified) {
                    return;
                }

                dirty = true;
                updateHandler.postDelayed(updateRunnable, updateDelay);
            }
        });

        setSyntaxColors(context);
        setUpdateDelay(CreekApplication.getCreekPreferences().getUpdateDelay());
        setTabWidth(CreekApplication.getCreekPreferences().getTabWidth());
    }

    private void setSyntaxColors(Context context) {
        colorError = ContextCompat.getColor(
                context,
                R.color.syntax_error);
        colorNumber = ContextCompat.getColor(
                context,
                R.color.syntax_number);
        colorKeyword = ContextCompat.getColor(
                context,
                R.color.syntax_keyword);
        colorBuiltin = ContextCompat.getColor(
                context,
                R.color.syntax_builtin);
        colorComment = ContextCompat.getColor(
                context,
                R.color.syntax_comment);
        colorPunctuation = ContextCompat.getColor(
                context,
                R.color.syntax_punctuation);
    }

    private void cancelUpdate() {
        updateHandler.removeCallbacks(updateRunnable);
    }

    private void highlightWithoutChange(Editable e) {
        modified = false;
        highlight(e);
        modified = true;
    }

    private Editable highlight(Editable e) {
        try {
            // don't use e.clearSpans() because it will
            // remove too much
            clearSpans(e);

            if (e.length() == 0) {
                return e;
            }

            if (errorLine > 0) {
                Matcher m = PATTERN_LINE.matcher(e);

                for (int i = errorLine; i-- > 0 && m.find(); ) {
                    // {} because analyzers don't like for (); statements
                }

                e.setSpan(
                        new BackgroundColorSpan(colorError),
                        m.start(),
                        m.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            for (Matcher m = PATTERN_NUMBERS.matcher(e); m.find(); ) {
                e.setSpan(
                        new ForegroundColorSpan(colorNumber),
                        m.start(),
                        m.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            for (Matcher m = PATTERN_PREPROCESSOR.matcher(e); m.find(); ) {
                e.setSpan(
                        new ForegroundColorSpan(colorKeyword),
                        m.start(),
                        m.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            for (Matcher m = PATTERN_KEYWORDS.matcher(e); m.find(); ) {
                e.setSpan(
                        new ForegroundColorSpan(colorKeyword),
                        m.start(),
                        m.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            for (Matcher m = PATTERN_BUILTINS.matcher(e); m.find(); ) {
                e.setSpan(
                        new ForegroundColorSpan(colorBuiltin),
                        m.start(),
                        m.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            for (Matcher m = PATTERN_COMMENTS.matcher(e); m.find(); ) {
                e.setSpan(
                        new ForegroundColorSpan(colorComment),
                        m.start(),
                        m.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            /*TODO for (Matcher m = PATTERN_COMMENTS.matcher(e); m.find(); ) {
                e.setSpan(
                        new ForegroundColorSpan(colorPunctuation),
                        m.start(),
                        m.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }*/
        } catch (IllegalStateException ex) {
            // raised by Matcher.start()/.end() when
            // no successful match has been made what
            // shouldn't ever happen because of find()
        }

        return e;
    }

    private static void clearSpans(Editable e) {
        // remove foreground color spans
        {
            ForegroundColorSpan spans[] = e.getSpans(
                    0,
                    e.length(),
                    ForegroundColorSpan.class);

            for (int i = spans.length; i-- > 0; ) {
                e.removeSpan(spans[i]);
            }
        }

        // remove background color spans
        {
            BackgroundColorSpan spans[] = e.getSpans(
                    0,
                    e.length(),
                    BackgroundColorSpan.class);

            for (int i = spans.length; i-- > 0; ) {
                e.removeSpan(spans[i]);
            }
        }
    }

    private CharSequence autoIndent(
            CharSequence source,
            Spanned dest,
            int dstart,
            int dend) {
        String indent = "";
        int istart = dstart - 1;

        // find start of this line
        boolean dataBefore = false;
        int pt = 0;

        for (; istart > -1; --istart) {
            char c = dest.charAt(istart);

            if (c == '\n') {
                break;
            }

            if (c != ' ' && c != '\t') {
                if (!dataBefore) {
                    // indent always after those characters
                    if (c == '{' ||
                            c == '+' ||
                            c == '-' ||
                            c == '*' ||
                            c == '/' ||
                            c == '%' ||
                            c == '^' ||
                            c == '=') {
                        --pt;
                    }

                    dataBefore = true;
                }

                // parenthesis counter
                if (c == '(') {
                    --pt;
                } else if (c == ')') {
                    ++pt;
                }
            }
        }

        // copy indent of this line into the next
        if (istart > -1) {
            char charAtCursor = dest.charAt(dstart);
            int iend;

            for (iend = ++istart; iend < dend; ++iend) {
                char c = dest.charAt(iend);

                // auto expand comments
                if (charAtCursor != '\n' &&
                        c == '/' &&
                        iend + 1 < dend &&
                        dest.charAt(iend) == c) {
                    iend += 2;
                    break;
                }

                if (c != ' ' && c != '\t') {
                    break;
                }
            }

            indent += dest.subSequence(istart, iend);
        }

        // add new indent
        if (pt < 0) {
            indent += "\t";
        }

        // append white space of previous line and new indent
        return source + indent;
    }

    private void convertTabs(Editable e, int start, int count) {
        if (tabWidth < 1) {
            return;
        }

        String s = e.toString();

        for (int stop = start + count;
             (start = s.indexOf("\t", start)) > -1 && start < stop;
             ++start) {
            e.setSpan(
                    new TabWidthSpan(tabWidth),
                    start,
                    start + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private static class TabWidthSpan extends ReplacementSpan {
        private int width;

        public TabWidthSpan(int width) {
            this.width = width;
        }

        @Override
        public int getSize(
                @NonNull Paint paint,
                CharSequence text,
                int start,
                int end,
                Paint.FontMetricsInt fm) {
            return width;
        }

        @Override
        public void draw(
                @NonNull Canvas canvas,
                CharSequence text,
                int start,
                int end,
                float x,
                int top,
                int y,
                int bottom,
                @NonNull Paint paint) {
        }
    }
}
