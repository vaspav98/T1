package t1.code.T1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    @Test
    public void testGetFrequency() throws Exception {
        Map<String, String> data = Map.of("text", "fdgdfgfgfgggfddddfggfgl;l,l,gfdg");

        MockHttpServletRequestBuilder request = post("/api")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                a -> a.node("g").isEqualTo(11),
                a -> a.node("f").isEqualTo(8),
                a -> a.node("d").isEqualTo(7),
                a -> a.node("l").isEqualTo(3),
                a -> a.node(",").isEqualTo(2),
                a -> a.node(";").isEqualTo(1)
        );

        LinkedHashMap<Character, Integer> map = om.readValue(body, LinkedHashMap.class);
        List<Integer> list = new ArrayList<>(map.values());

        assertThat(list.indexOf(11)).isEqualTo(0);
        assertThat(list.indexOf(7)).isEqualTo(2);
        assertThat(list.indexOf(1)).isEqualTo(5);
    }

    @Test
    public void testGetFrequency2() throws Exception {
        Map<String, String> data = Map.of("text", null);

        MockHttpServletRequestBuilder request = post("/api")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

}
