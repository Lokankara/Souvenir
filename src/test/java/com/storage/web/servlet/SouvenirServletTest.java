package com.storage.web.servlet;

import com.storage.model.dto.PostBrandDto;
import com.storage.model.dto.PostSouvenirDto;
import com.storage.service.IOSouvenirService;
import com.storage.service.facade.StorageFacade;
import com.storage.service.mapper.SouvenirMapper;
import com.storage.web.PageGenerator;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SouvenirServletTest {

    @InjectMocks
    private SouvenirServlet servlet;

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
        servlet = mock(SouvenirServlet.class);
        service = new IOSouvenirService(facade, mapper);
    }

    @Test
    void doGetTest() throws IOException {
        when(request.getParameter("page")).thenReturn("1");

        List<PostSouvenirDto> souvenirs = List.of(
                new PostSouvenirDto("Souvenir1", 10.0,
                        LocalDateTime.now(),
                        PostBrandDto
                                .builder()
                                .country("C")
                                .name("n")
                                .build()),
                new PostSouvenirDto("Souvenir", 10.0,
                        LocalDateTime.now(),
                        PostBrandDto
                                .builder()
                                .country("K")
                                .name("n")
                                .build())
        );
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        SouvenirServlet servlet = new SouvenirServlet();
        servlet.doGet(request, response);
        verify(request, times(1)).getParameter("page");
        verify(response, times(1)).getWriter();

        String result = stringWriter.toString();
    }
}
