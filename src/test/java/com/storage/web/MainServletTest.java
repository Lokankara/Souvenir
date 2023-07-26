package com.storage.web;

import com.storage.model.dto.PostBrandDto;
import com.storage.model.dto.PostSouvenirDto;
import com.storage.service.IOSouvenirService;
import com.storage.service.facade.StorageFacade;
import com.storage.service.mapper.SouvenirMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MainServletTest {

    @InjectMocks
    private MainServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private StorageFacade facade;
    private SouvenirMapper mapper;
    @Mock
    private IOSouvenirService service;
    PageGenerator pageGenerator;

    @BeforeEach
    void setUp() {
        pageGenerator = PageGenerator.instance();
        facade = mock(StorageFacade.class);
        mapper = mock(SouvenirMapper.class);
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        servlet = mock(MainServlet.class);
        service = new IOSouvenirService(facade, mapper);
//        service = mock(IOSouvenirService.class);
    }

    @Test
    void doGetTest() throws IOException {
        when(request.getParameter("page")).thenReturn("1");

        List<PostSouvenirDto> souvenirs = List.of(
                new PostSouvenirDto(1L, "Souvenir1", 10.0,
                        LocalDateTime.now(),
                        PostBrandDto.builder()
                                    .country("K")
                                    .name("n")
                                    .build()),
                new PostSouvenirDto(1L, "Souvenir1", 10.0,
                        LocalDateTime.now(),
                        PostBrandDto.builder()
                                    .country("K")
                                    .name("n")
                                    .build())
        );
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        MainServlet servlet = new MainServlet();
        servlet.doGet(request, response);

        verify(request, times(1)).getParameter("page");
        verify(response, times(1)).getWriter();

        String result = stringWriter.toString();
//        assertTrue(result.contains("Souvenir1"));
    }
}
