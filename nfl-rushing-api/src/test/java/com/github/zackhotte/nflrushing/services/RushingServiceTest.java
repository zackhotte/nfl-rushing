package com.github.zackhotte.nflrushing.services;

import com.github.zackhotte.nflrushing.DatabaseLoader;
import com.github.zackhotte.nflrushing.controllers.requests.RequestParam;
import com.github.zackhotte.nflrushing.models.Rusher;
import com.github.zackhotte.nflrushing.repositories.RusherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RushingServiceTest {

    @MockBean
    private RusherRepository rusherRepository;

    @Autowired
    private RushingService rushingService;

    @Value("classpath:rushing.json")
    private Resource resourceFile;

    @BeforeEach
    public void setup() throws IOException {
        List<Rusher> rushers = DatabaseLoader.getRushers(resourceFile);

        when(rusherRepository.getAllRushers()).thenReturn(rushers);
    }

    @ParameterizedTest
    @CsvSource({"asc, Taiwan Jones, -8", "desc, Isaiah Crowell, 85T"})
    public void verifyOrderIfSortedByLongestRun(String dir, String player, String longestRun) {
        var rushers = getAllRushersByDirection(dir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC);

        assertEquals(20, rushers.size());
        assertEquals(rushers.get(0).getPlayer(), player);
        assertEquals(rushers.get(0).getLongestRun(), longestRun);;
    }

    private List<Rusher> getAllRushersByDirection(Sort.Direction dir) {
        var pageable = PageRequest.of(0, 20, dir, "longestRun");
        var rushers = rushingService.getAll(new RequestParam(), pageable);

        return rushers.getContent();
    }
}
