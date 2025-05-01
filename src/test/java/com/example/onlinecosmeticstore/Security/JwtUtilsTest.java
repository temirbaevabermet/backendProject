package com.example.OnlineCosmeticStore.Security;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilsTest {

    @Test
    void testGenerateAndValidateToken() {
        JwtUtils jwtUtils = new JwtUtils();

        // Устанавливаем секрет и время жизни токена вручную
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret",
                "0afd5e2d36ebdbda9939e9230971dbbc2deaadfc3dfc515b2571bdaf889dd44e0af4849d8cecd760599c1713853468eee46cc23c0af1e2f2ce92706d715ff50d5ed46a14e038d7b2186893e0831fbf5ab501c65d931f9348100adfce6197e82beb0ac71349aeb63bca9c68f3fa86e45a07c9eb79c9854b413f1eab8dcfb4bef50ee2305fa748126f72f2534ee09d3ed2d8e13d6a6b0292070ecd7823de2653e1541306b1649a2052ac657501e4df5525bcb6b71ca45c6825d31082d1ac62050005884f6a7dcf4f580bdde0ee6f73755626275c7c4453db8fd917bcabfe2079c0caa1c94cc8b357eaa5967ad73b6141091dcd32533f61193042a0fdfa2ec669eba8cc69883fd1db42b2e39a1d023c6ab80c895ac732e49b515ab64005d2d0d472a93e616c820a2954e7321c76c03ce48c1a4201eacba6495f7d02b0d9795a14a2b5c1dee883cbec25873a7b6465b0d7dc715fb1f5c0539c78fc3c860a58e9df8b828c2e0a29922c770a6c09cced989117df8f504ab6eda3dc5d921e323f85b03c1975a071da54c89228e597523e4b6589d286b08644d2c4d6e0d1969795e80caeec7b9db44bb6863f8eaad816b5abc3f08342b83daea735d6081ebe94677a5237897faa992fb07bc9b58f6978901ed83e78d77c7ba6a1445bd703e0091a00320fe0e82c79b1001b28218c57e8c4c912191fdfc200a77e1a53f5c9bd88e652c8cb");
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 3600000); // 1 час

        String username = "testuser";
        String token = jwtUtils.generateJwtToken(username);

        assertNotNull(token);
        assertTrue(jwtUtils.validateJwtToken(token));
        assertEquals(username, jwtUtils.getUsernameFromJwtToken(token));
    }

    @Test
    void testInvalidTokenShouldFailValidation() {
        JwtUtils jwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", "same_long_secret_as_above");

        String invalidToken = "this.is.not.a.valid.token";

        assertFalse(jwtUtils.validateJwtToken(invalidToken));
    }
}
