package cinira.util

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.jasypt.iv.RandomIvGenerator
import org.junit.jupiter.api.Test
import java.util.*

class JasyptInteropTest {

    @Test
    fun `pbeDecrypt() for data encrypted by Jasypt`() {
        val encryptor = StandardPBEStringEncryptor()
        encryptor.setConfig(SimpleStringPBEConfig().apply {
            password = "testPassword"
            algorithm = PBE_ALGORITHM
            ivGenerator = RandomIvGenerator()
            keyObtentionIterations = PBE_ITERATIONS
            stringOutputType = "base64"
        })
        val encrypted = encryptor.encrypt("This is only a test.")
        val decrypted = pbeDecrypt("testPassword", Base64.getDecoder().decode(encrypted))
        assertThat(String(decrypted)).isEqualTo("This is only a test.")
    }

    @Test
    fun `pbeEncrypt() for data decrypted by Jasypt`() {
        val encrypted = pbeEncrypt("testPassword", "This is only a test.".toByteArray())
        val encryptor = StandardPBEStringEncryptor()
        encryptor.setConfig(SimpleStringPBEConfig().apply {
            password = "testPassword"
            algorithm = PBE_ALGORITHM
            ivGenerator = RandomIvGenerator()
            keyObtentionIterations = PBE_ITERATIONS
            stringOutputType = "base64"
        })
        val decrypted = encryptor.decrypt(String(Base64.getEncoder().encode(encrypted)))
        assertThat(decrypted).isEqualTo("This is only a test.")
    }
}