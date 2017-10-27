package com.sortedqueue.programmercreek.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.InputFilter
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ReplacementSpan
import android.util.AttributeSet

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by Alok on 14/09/17.
 */

class CodeEditor : AppCompatEditText {

    interface OnTextChangedListener {
        fun onTextChanged(text: String)
    }

    private val updateHandler = Handler()
    private val updateRunnable = Runnable {
        val e = text

        if (onTextChangedListener != null) {
            onTextChangedListener!!.onTextChanged(
                    e.toString())
        }

        highlightWithoutChange(e)
    }

    private var onTextChangedListener: OnTextChangedListener? = null
    private var updateDelay = 1000
    private var errorLine = 0
    var isModified = false
        private set
    private var modified = true
    private var colorError: Int = 0
    private var colorNumber: Int = 0
    private var colorKeyword: Int = 0
    private var colorBuiltin: Int = 0
    private var colorComment: Int = 0
    private var colorPunctuation: Int = 0
    private var tabWidthInCharacters = 0
    private var tabWidth = 0

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    fun setOnTextChangedListener(listener: OnTextChangedListener) {
        onTextChangedListener = listener
    }

    fun setUpdateDelay(ms: Int) {
        updateDelay = ms
    }

    fun setTabWidth(characters: Int) {
        if (tabWidthInCharacters == characters) {
            return
        }

        tabWidthInCharacters = characters
        tabWidth = Math.round(paint.measureText("m") * characters)
    }

    fun hasErrorLine(): Boolean {
        return errorLine > 0
    }

    fun setErrorLine(line: Int) {
        errorLine = line
    }

    fun updateHighlighting() {
        highlightWithoutChange(text)
    }

    fun setTextHighlighted(text: CharSequence?) {
        var text = text
        if (text == null) {
            text = ""
        }

        cancelUpdate()

        errorLine = 0
        isModified = false

        modified = false
        setText(highlight(SpannableStringBuilder(text)))
        modified = true

        if (onTextChangedListener != null) {
            onTextChangedListener!!.onTextChanged(text.toString())
        }
    }

    val cleanText: String
        get() = PATTERN_TRAILING_WHITE_SPACE
                .matcher(text)
                .replaceAll("")

    fun insertTab() {
        val start = selectionStart
        val end = selectionEnd

        text.replace(
                Math.min(start, end),
                Math.max(start, end),
                "\t",
                0,
                1)
    }

    fun addUniform(statement: String?) {
        var statement: String? = statement ?: return

        val e = text
        removeUniform(e, statement)

        val m = PATTERN_INSERT_UNIFORM.matcher(e)
        var start = -1

        while (m.find()) {
            start = m.end()
        }

        if (start > -1) {
            // add line break before statement because it's
            // inserted before the last line-break
            statement = "\n" + statement
        } else {
            // add a line break after statement if there's no
            // uniform already
            statement += "\n"

            // add an empty line between the last #endif
            // and the now following uniform
            start = endIndexOfLastEndIf(e)
            if ((start) > -1) {
                statement = "\n" + statement
            }

            // move index past line break or to the start
            // of the text when no #endif was found
            ++start
        }

        e.insert(start, statement)
    }

    private fun removeUniform(e: Editable, statement: String?) {
        if (statement == null) {
            return
        }

        var regex = "^(" + statement.replace(" ", "[ \\t]+")
        val p = regex.indexOf(";")
        if (p > -1) {
            regex = regex.substring(0, p)
        }
        regex += ".*\\n)$"

        val m = Pattern.compile(regex, Pattern.MULTILINE).matcher(e)
        if (m.find()) {
            e.delete(m.start(), m.end())
        }
    }

    private fun endIndexOfLastEndIf(e: Editable): Int {
        val m = PATTERN_ENDIF.matcher(e)
        var idx = -1

        while (m.find()) {
            idx = m.end()
        }

        return idx
    }

    private fun init(context: Context) {
        setHorizontallyScrolling(true)

        filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
            if (modified &&
                    end - start == 1 &&
                    start < source.length &&
                    dstart < dest.length) {
                val c = source[start]

                if (c == '\n') {
                    return@InputFilter autoIndent(source, dest, dstart, dend)
                }
            }

            source
        })

        addTextChangedListener(object : TextWatcher {
            private var start = 0
            private var count = 0

            override fun onTextChanged(
                    s: CharSequence,
                    start: Int,
                    before: Int,
                    count: Int) {
                this.start = start
                this.count = count
            }

            override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int) {
            }

            override fun afterTextChanged(e: Editable) {
                cancelUpdate()
                convertTabs(e, start, count)

                if (!modified) {
                    return
                }

                isModified = true
                updateHandler.postDelayed(updateRunnable, updateDelay.toLong())
            }
        })

        setSyntaxColors(context)
        setUpdateDelay(CreekApplication.creekPreferences!!.updateDelay)
        setTabWidth(CreekApplication.creekPreferences!!.tabWidth)
    }

    private fun setSyntaxColors(context: Context) {
        colorError = ContextCompat.getColor(
                context,
                R.color.syntax_error)
        colorNumber = ContextCompat.getColor(
                context,
                R.color.syntax_number)
        colorKeyword = ContextCompat.getColor(
                context,
                R.color.syntax_keyword)
        colorBuiltin = ContextCompat.getColor(
                context,
                R.color.syntax_builtin)
        colorComment = ContextCompat.getColor(
                context,
                R.color.syntax_comment)
        colorPunctuation = ContextCompat.getColor(
                context,
                R.color.syntax_punctuation)
    }

    private fun cancelUpdate() {
        updateHandler.removeCallbacks(updateRunnable)
    }

    private fun highlightWithoutChange(e: Editable) {
        modified = false
        highlight(e)
        modified = true
    }

    private fun highlight(e: Editable): Editable {
        try {
            // don't use e.clearSpans() because it will
            // remove too much
            clearSpans(e)

            if (e.length == 0) {
                return e
            }

            if (errorLine > 0) {
                val m = PATTERN_LINE.matcher(e)

                var i = errorLine
                while (i-- > 0 && m.find()) {
                    // {} because analyzers don't like for (); statements
                }

                e.setSpan(
                        BackgroundColorSpan(colorError),
                        m.start(),
                        m.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            run {
                val m = PATTERN_NUMBERS.matcher(e)
                while (m.find()) {
                    e.setSpan(
                            ForegroundColorSpan(colorNumber),
                            m.start(),
                            m.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }

            run {
                val m = PATTERN_PREPROCESSOR.matcher(e)
                while (m.find()) {
                    e.setSpan(
                            ForegroundColorSpan(colorKeyword),
                            m.start(),
                            m.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }

            run {
                val m = PATTERN_KEYWORDS.matcher(e)
                while (m.find()) {
                    e.setSpan(
                            ForegroundColorSpan(colorKeyword),
                            m.start(),
                            m.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }

            run {
                val m = PATTERN_BUILTINS.matcher(e)
                while (m.find()) {
                    e.setSpan(
                            ForegroundColorSpan(colorBuiltin),
                            m.start(),
                            m.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }

            val m = PATTERN_COMMENTS.matcher(e)
            while (m.find()) {
                e.setSpan(
                        ForegroundColorSpan(colorComment),
                        m.start(),
                        m.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            /*TODO for (Matcher m = PATTERN_COMMENTS.matcher(e); m.find(); ) {
                e.setSpan(
                        new ForegroundColorSpan(colorPunctuation),
                        m.start(),
                        m.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }*/
        } catch (ex: IllegalStateException) {
            // raised by Matcher.start()/.end() when
            // no successful match has been made what
            // shouldn't ever happen because of find()
        }

        return e
    }

    private fun autoIndent(
            source: CharSequence,
            dest: Spanned,
            dstart: Int,
            dend: Int): CharSequence {
        var indent = ""
        var istart = dstart - 1

        // find start of this line
        var dataBefore = false
        var pt = 0

        while (istart > -1) {
            val c = dest[istart]

            if (c == '\n') {
                break
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
                        --pt
                    }

                    dataBefore = true
                }

                // parenthesis counter
                if (c == '(') {
                    --pt
                } else if (c == ')') {
                    ++pt
                }
            }
            --istart
        }

        // copy indent of this line into the next
        if (istart > -1) {
            val charAtCursor = dest[dstart]
            var iend: Int

            iend = ++istart
            while (iend < dend) {
                val c = dest[iend]

                // auto expand comments
                if (charAtCursor != '\n' &&
                        c == '/' &&
                        iend + 1 < dend &&
                        dest[iend] == c) {
                    iend += 2
                    break
                }

                if (c != ' ' && c != '\t') {
                    break
                }
                ++iend
            }

            indent += dest.subSequence(istart, iend)
        }

        // add new indent
        if (pt < 0) {
            indent += "\t"
        }

        // append white space of previous line and new indent
        return source.toString() + indent
    }

    private fun convertTabs(e: Editable, start: Int, count: Int) {
        var tabStart = start
        if (tabWidth < 1) {
            return
        }

        val s = e.toString()

        val stop = tabStart + count
        tabStart = s.indexOf("\t", tabStart)
        while ((tabStart) > -1 && tabStart < stop) {
            e.setSpan(
                    TabWidthSpan(tabWidth),
                    tabStart,
                    tabStart + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            ++tabStart
            tabStart = s.indexOf("\t", tabStart)
        }
    }

    private class TabWidthSpan(private val width: Int) : ReplacementSpan() {

        override fun getSize(
                paint: Paint,
                text: CharSequence,
                start: Int,
                end: Int,
                fm: Paint.FontMetricsInt?): Int {
            return width
        }

        override fun draw(
                canvas: Canvas,
                text: CharSequence,
                start: Int,
                end: Int,
                x: Float,
                top: Int,
                y: Int,
                bottom: Int,
                paint: Paint) {
        }
    }

    companion object {

        private val PATTERN_LINE = Pattern.compile(
                ".*\\n")
        private val PATTERN_NUMBERS = Pattern.compile(
                "\\b(\\d*[.]?\\d+)\\b")
        private val PATTERN_PREPROCESSOR = Pattern.compile(
                "^[\t ]*(#define|#undef|#if|#ifdef|#ifndef|#else|#elif|#endif|" + "#error|#pragma|#extension|#version|#line|#include)\\b",
                Pattern.MULTILINE)
        private val PATTERN_KEYWORDS = Pattern.compile(
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
                        "|printf|scanf|gets|puts|println|getchar|putchar|getch|exception|perror|cin|cout)\\b")
        private val PATTERN_BUILTINS = Pattern.compile(
                "\\b(radians|degrees|sin|cos|tan|asin|acos|atan|pow|" +
                        "exp|log|exp2|log2|sqrt|inversesqrt|abs|sign|floor|ceil|fract|mod|" +
                        "min|max|clamp|mix|step|smoothstep|length|distance|dot|cross|" +
                        "normalize|faceforward|reflect|refract|matrixCompMult|lessThan|" +
                        "lessThanEqual|greaterThan|greaterThanEqual|equal|notEqual|any|all|" +
                        "not|dFdx|dFdy|fwidth|texture2D|texture2DProj|texture2DLod|" +
                        "texture2DProjLod|textureCube|textureCubeLod)\\b")
        private val PATTERN_COMMENTS = Pattern.compile(
                "/\\*(?:.|[\\n\\r])*?\\*/|//.*")
        private val PATTERN_TRAILING_WHITE_SPACE = Pattern.compile(
                "[\\t ]+$",
                Pattern.MULTILINE)
        private val PATTERN_INSERT_UNIFORM = Pattern.compile(
                "^([ \t]*uniform.+)$",
                Pattern.MULTILINE)
        private val PATTERN_ENDIF = Pattern.compile(
                "(#endif)\\b")

        private fun clearSpans(e: Editable) {
            // remove foreground color spans
            run {
                val spans = e.getSpans(
                        0,
                        e.length,
                        ForegroundColorSpan::class.java)

                var i = spans.size
                while (i-- > 0) {
                    e.removeSpan(spans[i])
                }
            }

            // remove background color spans
            run {
                val spans = e.getSpans(
                        0,
                        e.length,
                        BackgroundColorSpan::class.java)

                var i = spans.size
                while (i-- > 0) {
                    e.removeSpan(spans[i])
                }
            }
        }
    }
}
