package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin

class SpiritualSoundManager(private val context: Context) {

    private val scope = CoroutineScope(Dispatchers.Default)
    private var omDroneJob: Job? = null
    private var chantJob: Job? = null

    private val _isOmDronePlaying = MutableStateFlow(false)
    val isOmDronePlaying: StateFlow<Boolean> = _isOmDronePlaying.asStateFlow()

    private val _isAartiPlaying = MutableStateFlow(false)
    val isAartiPlaying: StateFlow<Boolean> = _isAartiPlaying.asStateFlow()

    private val vibrator: Vibrator? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
    }

    fun vibrateBead() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(35, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(35)
            }
        } catch (_: Exception) {}
    }

    fun vibrateCelebration() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val timings = longArrayOf(0, 100, 80, 150, 80, 250)
                val amplitudes = intArrayOf(0, 200, 0, 255, 0, 255)
                vibrator?.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(longArrayOf(0, 100, 80, 150, 80, 250), -1)
            }
        } catch (_: Exception) {}
    }

    /**
     * Plays authentic synthesized temple bell chime (Ghanti)
     */
    fun playTempleBell() {
        scope.launch {
            vibrateBead()
            playSynthesizedTone(
                sampleRate = 44100,
                durationSeconds = 2.4f,
                frequencies = listOf(
                    FrequencyComponent(freq = 1174.66f, amplitude = 0.5f, decay = 2.2f), // D6
                    FrequencyComponent(freq = 2349.32f, amplitude = 0.35f, decay = 3.5f), // D7
                    FrequencyComponent(freq = 3520.0f, amplitude = 0.2f, decay = 5.0f),  // A7
                    FrequencyComponent(freq = 587.33f, amplitude = 0.3f, decay = 1.4f)   // D5 strike tone
                )
            )
        }
    }

    /**
     * Plays sacred synthesized Shankh (conch) resonant naad
     */
    fun playShankhNaad() {
        scope.launch {
            playShankhAcoustics(durationSeconds = 3.5f)
        }
    }

    /**
     * Plays wooden mala bead click sound
     */
    fun playMalaClick() {
        scope.launch {
            vibrateBead()
            playSynthesizedTone(
                sampleRate = 22050,
                durationSeconds = 0.08f,
                frequencies = listOf(
                    FrequencyComponent(freq = 800f, amplitude = 0.6f, decay = 40.0f),
                    FrequencyComponent(freq = 1400f, amplitude = 0.4f, decay = 60.0f)
                )
            )
        }
    }

    /**
     * Starts continuous sacred Om (136.1 Hz Cosmic tone) drone loop
     */
    fun toggleOmDrone() {
        if (_isOmDronePlaying.value) {
            stopOmDrone()
        } else {
            startOmDrone()
        }
    }

    fun startOmDrone() {
        if (_isOmDronePlaying.value) return
        _isOmDronePlaying.value = true
        omDroneJob = scope.launch {
            val sampleRate = 22050
            val bufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            ).coerceAtLeast(sampleRate)

            var track: AudioTrack? = null
            try {
                track = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(bufferSize * 2)
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .build()

                track.play()

                val buffer = ShortArray(bufferSize)
                var phase1 = 0.0
                var phase2 = 0.0
                var phase3 = 0.0
                val freq1 = 136.1 // Sacred OM frequency
                val freq2 = 272.2 // 1st harmonic
                val freq3 = 408.3 // 2nd harmonic

                while (isActive && _isOmDronePlaying.value) {
                    for (i in buffer.indices) {
                        val s1 = sin(phase1) * 0.45
                        val s2 = sin(phase2) * 0.25
                        val s3 = sin(phase3) * 0.12
                        // Gentle slow breathing modulation
                        val mod = 0.85 + 0.15 * sin(phase1 * 0.02)
                        val sample = ((s1 + s2 + s3) * mod * Short.MAX_VALUE).toInt()
                            .coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt())
                        buffer[i] = sample.toShort()

                        phase1 += 2 * PI * freq1 / sampleRate
                        if (phase1 >= 2 * PI) phase1 -= 2 * PI
                        phase2 += 2 * PI * freq2 / sampleRate
                        if (phase2 >= 2 * PI) phase2 -= 2 * PI
                        phase3 += 2 * PI * freq3 / sampleRate
                        if (phase3 >= 2 * PI) phase3 -= 2 * PI
                    }
                    track.write(buffer, 0, buffer.size)
                }
            } catch (_: Exception) {
            } finally {
                try {
                    track?.stop()
                    track?.release()
                } catch (_: Exception) {}
                _isOmDronePlaying.value = false
            }
        }
    }

    fun stopOmDrone() {
        _isOmDronePlaying.value = false
        omDroneJob?.cancel()
        omDroneJob = null
    }

    /**
     * Plays rhythmic aarti bells & percussion simulation
     */
    fun toggleAartiRhythm() {
        if (_isAartiPlaying.value) {
            stopAartiRhythm()
        } else {
            startAartiRhythm()
        }
    }

    fun startAartiRhythm() {
        if (_isAartiPlaying.value) return
        _isAartiPlaying.value = true
        chantJob = scope.launch {
            try {
                while (isActive && _isAartiPlaying.value) {
                    playTempleBell()
                    delay(700)
                    if (!isActive || !_isAartiPlaying.value) break
                    playMalaClick()
                    delay(350)
                    playMalaClick()
                    delay(350)
                }
            } finally {
                _isAartiPlaying.value = false
            }
        }
    }

    fun stopAartiRhythm() {
        _isAartiPlaying.value = false
        chantJob?.cancel()
        chantJob = null
    }

    private data class FrequencyComponent(val freq: Float, val amplitude: Float, val decay: Float)

    private fun playSynthesizedTone(
        sampleRate: Int,
        durationSeconds: Float,
        frequencies: List<FrequencyComponent>
    ) {
        val totalSamples = (sampleRate * durationSeconds).toInt()
        val buffer = ShortArray(totalSamples)

        for (i in 0 until totalSamples) {
            val t = i.toFloat() / sampleRate
            var sampleVal = 0.0
            for (comp in frequencies) {
                val envelope = exp(-comp.decay * t)
                sampleVal += comp.amplitude * envelope * sin(2.0 * PI * comp.freq * t)
            }
            val clamped = (sampleVal * Short.MAX_VALUE).toInt()
                .coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt())
            buffer[i] = clamped.toShort()
        }

        var track: AudioTrack? = null
        try {
            track = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(buffer.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            track.write(buffer, 0, buffer.size)
            track.play()
            Thread.sleep((durationSeconds * 1000).toLong().coerceAtLeast(100))
        } catch (_: Exception) {
        } finally {
            try {
                track?.stop()
                track?.release()
            } catch (_: Exception) {}
        }
    }

    private fun playShankhAcoustics(durationSeconds: Float) {
        val sampleRate = 44100
        val totalSamples = (sampleRate * durationSeconds).toInt()
        val buffer = ShortArray(totalSamples)

        val baseFreq = 220.0 // A3 conch fundamental

        for (i in 0 until totalSamples) {
            val t = i.toFloat() / sampleRate
            // Conch swell envelope (build up then steady then gradual fade)
            val envelope = when {
                t < 0.6f -> (t / 0.6f) * (t / 0.6f)
                t < durationSeconds - 0.8f -> 1.0f
                else -> {
                    val fadeTime = t - (durationSeconds - 0.8f)
                    (1.0f - (fadeTime / 0.8f)).coerceAtLeast(0.0f)
                }
            }

            // Slight vibrato
            val vibrato = 1.0 + 0.02 * sin(2.0 * PI * 5.5 * t)
            val currentFreq = baseFreq * vibrato

            val h1 = sin(2.0 * PI * currentFreq * t) * 0.55
            val h2 = sin(2.0 * PI * currentFreq * 2.0 * t) * 0.35
            val h3 = sin(2.0 * PI * currentFreq * 3.0 * t) * 0.18
            val h4 = sin(2.0 * PI * currentFreq * 4.0 * t) * 0.10

            val sampleVal = (h1 + h2 + h3 + h4) * envelope
            val clamped = (sampleVal * Short.MAX_VALUE).toInt()
                .coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt())
            buffer[i] = clamped.toShort()
        }

        var track: AudioTrack? = null
        try {
            track = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(buffer.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            track.write(buffer, 0, buffer.size)
            track.play()
            Thread.sleep((durationSeconds * 1000).toLong().coerceAtLeast(100))
        } catch (_: Exception) {
        } finally {
            try {
                track?.stop()
                track?.release()
            } catch (_: Exception) {}
        }
    }
}
