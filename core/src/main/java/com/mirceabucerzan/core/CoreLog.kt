package com.mirceabucerzan.core

import android.os.Build
import org.jetbrains.annotations.NonNls
import timber.log.Timber
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Wraps [Timber]'s logging calls. The [Timber] library must be
 * initialized before calling any of these methods.
 *
 * The log tag is manually set based on the correct stack element.
 */
class CoreLog {

    companion object {
        /** Log a verbose message with optional format args.  */
        fun v(@NonNls message: String, vararg args: Any) {
            Timber.tag(getTag()).v(message, args)
        }

        /** Log a verbose exception and a message with optional format args.  */
        fun v(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.tag(getTag()).v(t, message, args)
        }

        /** Log a verbose exception.  */
        fun v(t: Throwable) {
            Timber.tag(getTag()).v(t)
        }

        /** Log a debug message with optional format args.  */
        fun d(@NonNls message: String, vararg args: Any) {
            Timber.tag(getTag()).d(message, args)
        }

        /** Log a debug exception and a message with optional format args.  */
        fun d(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.tag(getTag()).d(t, message, args)
        }

        /** Log a debug exception.  */
        fun d(t: Throwable) {
            Timber.tag(getTag()).d(t)
        }

        /** Log an info message with optional format args.  */
        fun i(@NonNls message: String, vararg args: Any) {
            Timber.tag(getTag()).i(message, args)
        }

        /** Log an info exception and a message with optional format args.  */
        fun i(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.tag(getTag()).i(t, message, args)
        }

        /** Log an info exception.  */
        fun i(t: Throwable) {
            Timber.tag(getTag()).i(t)
        }

        /** Log a warning message with optional format args.  */
        fun w(@NonNls message: String, vararg args: Any) {
            Timber.tag(getTag()).w(message, args)
        }

        /** Log a warning exception and a message with optional format args.  */
        fun w(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.tag(getTag()).w(t, message, args)
        }

        /** Log a warning exception.  */
        fun w(t: Throwable) {
            Timber.tag(getTag()).w(t)
        }

        /** Log an error message with optional format args.  */
        fun e(@NonNls message: String, vararg args: Any) {
            Timber.tag(getTag()).e(message, args)
        }

        /** Log an error exception and a message with optional format args.  */
        fun e(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.tag(getTag()).e(t, message, args)
        }

        /** Log an error exception.  */
        fun e(t: Throwable) {
            Timber.tag(getTag()).e(t)
        }

        /** Log an assert message with optional format args.  */
        fun wtf(@NonNls message: String, vararg args: Any) {
            Timber.tag(getTag()).wtf(message, args)
        }

        /** Log an assert exception and a message with optional format args.  */
        fun wtf(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.tag(getTag()).wtf(t, message, args)
        }

        /** Log an assert exception.  */
        fun wtf(t: Throwable) {
            Timber.tag(getTag()).wtf(t)
        }

        /**
         * Copied from [Timber.DebugTree] because it is package-private and cannot be
         * overridden in [Timber.Tree] subclasses.
         */
        private fun getTag(): String {
            // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
            // because Robolectric runs them on the JVM but on Android the elements are different.
            val stackTrace: Array<StackTraceElement> = Throwable().stackTrace
            if (stackTrace.size <= CALL_STACK_INDEX) {
                throw IllegalStateException(
                    "Synthetic stacktrace didn't have enough elements: are you using proguard?"
                )
            }
            return createStackElementTag(stackTrace[CALL_STACK_INDEX])
        }

        /**
         * Copied from [Timber.DebugTree].
         *
         * Extract the tag which should be used for the message from the `element`. By default
         * this will use the class name without any anonymous class suffixes (e.g., `Foo$1`
         * becomes `Foo`).
         */
        private fun createStackElementTag(element: StackTraceElement): String {
            var tag: String = element.className
            val m: Matcher = ANONYMOUS_CLASS.matcher(tag)
            if (m.find()) {
                tag = m.replaceAll("")
            }
            tag = tag.substring(tag.lastIndexOf('.') + 1)
            // Tag length limit was removed in API 24.
            return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tag
            } else tag.substring(0, MAX_TAG_LENGTH)
        }

        private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
        /* Changed index to retrieve the proper tag since the call
        stack has changed after wrapping Timber */
        private const val CALL_STACK_INDEX = 2
        private const val MAX_TAG_LENGTH = 23
    }

}