package com.acorn.testanything.retrofit

import android.text.TextUtils
import okhttp3.Request
import okio.Buffer
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLDecoder

internal class Printer private constructor() {

    init {
        throw UnsupportedOperationException()
    }

    companion object {

        private val JSON_INDENT = 3

        private val LINE_SEPARATOR = System.getProperty("line.separator")
        private val DOUBLE_SEPARATOR = LINE_SEPARATOR + LINE_SEPARATOR

        private val OMITTED_RESPONSE = arrayOf(LINE_SEPARATOR, "Omitted response body")
        private val OMITTED_REQUEST = arrayOf(LINE_SEPARATOR, "Omitted request body")

        private val N = "\n"
        private val T = "\t"
        private val REQUEST_UP_LINE = "┌────── Request ────────────────────────────────────────────────────────────────────────"
        private val END_LINE = "└───────────────────────────────────────────────────────────────────────────────────────"
        private val RESPONSE_UP_LINE = "┌────── Response ───────────────────────────────────────────────────────────────────────"
        private val BODY_TAG = "Body:"
        private val URL_TAG = "URL: "
        private val METHOD_TAG = "Method: @"
        private val HEADERS_TAG = "Headers:"
        private val STATUS_CODE_TAG = "Status Code: "
        private val RECEIVED_TAG = "Received in: "
        private val CORNER_UP = "┌ "
        private val CORNER_BOTTOM = "└ "
        private val CENTER_LINE = "├ "
        private val DEFAULT_LINE = "│ "

        private fun isEmpty(line: String): Boolean {
            return TextUtils.isEmpty(line) || N == line || T == line || TextUtils.isEmpty(line.trim { it <= ' ' })
        }

        fun printJsonRequest(builder: LoggingInterceptor.Builder, request: Request) {
            val requestBody = LINE_SEPARATOR + BODY_TAG + LINE_SEPARATOR + bodyToString(request)
            val tag = builder.getTag(true)
            if (builder.logger == null)
                I.log(builder.type, tag, REQUEST_UP_LINE)
            logLines(builder.type, tag, getRequestUrl(request), builder.logger, false)
            logLines(builder.type, tag, getRequest(request, builder.getLevel()), builder.logger, true)
            if (builder.getLevel() === Level.BASIC || builder.getLevel() === Level.BODY) {
                logLines(builder.type, tag, requestBody.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray(), builder.logger, true)
            }
            if (builder.logger == null)
                I.log(builder.type, tag, END_LINE)
        }

        fun printJsonResponse(builder: LoggingInterceptor.Builder, chainMs: Long, isSuccessful: Boolean,
                              code: Int, headers: String, bodyString: String, segments: List<String>, message: String, responseUrl: String) {

            val responseBody = LINE_SEPARATOR + BODY_TAG + LINE_SEPARATOR + getJsonString(bodyString)
            val tag = builder.getTag(false)
            val urlLine = arrayOf(URL_TAG + responseUrl, N)
            val response = getResponse(headers, chainMs, code, isSuccessful,
                    builder.getLevel(), segments, message)

            if (builder.logger == null) {
                I.log(builder.type, tag, RESPONSE_UP_LINE)
            }

            logLines(builder.type, tag, urlLine, builder.logger, true)
            logLines(builder.type, tag, response, builder.logger, true)

            if (builder.getLevel() === Level.BASIC || builder.getLevel() === Level.BODY) {
                logLines(builder.type, tag, responseBody.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray(), builder.logger, true)
            }
            if (builder.logger == null) {
                I.log(builder.type, tag, END_LINE)
            }
        }

        fun printFileRequest(builder: LoggingInterceptor.Builder, request: Request) {
            val tag = builder.getTag(true)
            if (builder.logger == null)
                I.log(builder.type, tag, REQUEST_UP_LINE)
            logLines(builder.type, tag, arrayOf(URL_TAG + request.url()), builder.logger, false)
            logLines(builder.type, tag, getRequest(request, builder.getLevel()), builder.logger, true)
            if (builder.getLevel() === Level.BASIC || builder.getLevel() === Level.BODY) {
                logLines(builder.type, tag, OMITTED_REQUEST, builder.logger, true)
            }
            if (builder.logger == null)
                I.log(builder.type, tag, END_LINE)
        }

        fun printFileResponse(builder: LoggingInterceptor.Builder, chainMs: Long, isSuccessful: Boolean,
                              code: Int, headers: String, segments: List<String>, message: String) {
            val tag = builder.getTag(false)
            if (builder.logger == null)
                I.log(builder.type, tag, RESPONSE_UP_LINE)
            logLines(builder.type, tag, getResponse(headers, chainMs, code, isSuccessful,
                    builder.getLevel(), segments, message), builder.logger, true)
            logLines(builder.type, tag, OMITTED_RESPONSE, builder.logger, true)
            if (builder.logger == null) I.log(builder.type, tag, END_LINE)
        }

        private fun getRequest(request: Request, level: Level): Array<String> {
            val log: String
            val header = request.headers().toString()
            val loggableHeader = level === Level.HEADERS || level === Level.BASIC
            log = METHOD_TAG + request.method() + DOUBLE_SEPARATOR +
                    if (isEmpty(header)) "" else if (loggableHeader) HEADERS_TAG + LINE_SEPARATOR + dotHeaders(header) else ""
            return log.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }

        private fun getResponse(header: String, tookMs: Long, code: Int, isSuccessful: Boolean,
                                level: Level, segments: List<String>, message: String): Array<String> {
            val log: String
            val loggableHeader = level === Level.HEADERS || level === Level.BASIC
            val segmentString = slashSegments(segments)
            log = ((if (!TextUtils.isEmpty(segmentString)) "$segmentString - " else "") + "is success : "
                    + isSuccessful + " - " + RECEIVED_TAG + tookMs + "ms" + DOUBLE_SEPARATOR + STATUS_CODE_TAG +
                    code + " / " + message + DOUBLE_SEPARATOR + if (isEmpty(header))
                ""
            else if (loggableHeader)
                HEADERS_TAG + LINE_SEPARATOR +
                        dotHeaders(header)
            else
                "")
            return log.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }

        private fun slashSegments(segments: List<String>): String {
            val segmentString = StringBuilder()
            for (segment in segments) {
                segmentString.append("/").append(segment)
            }
            return segmentString.toString()
        }

        private fun dotHeaders(header: String): String {
            val headers = header.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val builder = StringBuilder()
            var tag = "─ "
            if (headers.size > 1) {
                for (i in headers.indices) {
                    if (i == 0) {
                        tag = CORNER_UP
                    } else if (i == headers.size - 1) {
                        tag = CORNER_BOTTOM
                    } else {
                        tag = CENTER_LINE
                    }
                    builder.append(tag).append(headers[i]).append("\n")
                }
            } else {
                for (item in headers) {
                    builder.append(tag).append(item).append("\n")
                }
            }
            return builder.toString()
        }

        private fun logLines(type: Int, tag: String, lines: Array<String>, logger: Logger?, withLineSize: Boolean) {
            for (line in lines) {
                val lineLength = line.length
                val MAX_LONG_SIZE = if (withLineSize) 110 else lineLength
                for (i in 0..lineLength / MAX_LONG_SIZE) {
                    val start = i * MAX_LONG_SIZE
                    var end = (i + 1) * MAX_LONG_SIZE
                    end = if (end > line.length) line.length else end
                    if (logger == null) {
                        I.log(type, tag, DEFAULT_LINE + line.substring(start, end))
                    } else {
                        logger.log(type, tag, line.substring(start, end))
                    }
                }
            }
        }

        private fun bodyToString(request: Request): String {
            try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                if (copy.body() == null)
                    return ""
                copy.body()!!.writeTo(buffer)
                return getJsonString(buffer.readUtf8())
            } catch (e: IOException) {
                return "{\"err\": \"" + e.message + "\"}"
            }

        }

        fun getJsonString(msg: String): String {
            var message: String
            try {
                if (msg.startsWith("{")) {
                    val jsonObject = JSONObject(msg)
                    message = jsonObject.toString(JSON_INDENT)
                } else if (msg.startsWith("[")) {
                    val jsonArray = JSONArray(msg)
                    message = jsonArray.toString(JSON_INDENT)
                } else {
                    message = URLDecoder.decode(msg, "utf-8")
                }
            } catch (e: JSONException) {
                message = msg
            } catch (e: UnsupportedEncodingException) {
                message = msg
            }

            return message
        }

        private fun getRequestUrl(request: Request): Array<String> {
            if ("GET" == request.method()) {
                val text = URL_TAG + request.url()
                try {
                    return arrayOf(URLDecoder.decode(text, "utf-8"))
                } catch (e: UnsupportedEncodingException) {
                    return arrayOf(text)
                }

            } else {
                return arrayOf(URL_TAG + request.url())
            }

        }
    }
}
