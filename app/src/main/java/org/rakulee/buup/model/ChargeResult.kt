package org.rakulee.buup.model

class ChargeResult private constructor(
    val success: Boolean, val networkError: Boolean,
    /**
     * Set if [.success] is false and [.networkError] is false.
     */
    val errorMessage: String?
) {
    companion object {
        fun success(): ChargeResult {
            return ChargeResult(true, false, null)
        }

        fun error(message: String?): ChargeResult {
            return ChargeResult(false, false, message)
        }

        fun networkError(): ChargeResult {
            return ChargeResult(false, true, null)
        }
    }
}