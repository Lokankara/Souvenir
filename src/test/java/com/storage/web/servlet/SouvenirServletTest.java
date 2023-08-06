package com.storage.web.servlet;

import com.storage.service.IOSouvenirService;
import com.storage.service.facade.StorageFacade;
import com.storage.service.mapper.SouvenirMapper;
import com.storage.web.PageGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SouvenirServletTest {

    @InjectMocks
    private SouvenirServlet servlet;

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    private StorageFacade facade;
    private SouvenirMapper mapper;
    @Mock
    private IOSouvenirService service;
    PageGenerator pageGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pageGenerator = PageGenerator.instance();
        facade = mock(StorageFacade.class);
        mapper = mock(SouvenirMapper.class);
        resp = mock(HttpServletResponse.class);
        req = mock(HttpServletRequest.class);
        servlet = mock(SouvenirServlet.class);
        service = mock(IOSouvenirService.class);
    }


//    @Test
//    void doGetTest() throws IOException {
//        when(request.getParameter("page")).thenReturn("1");
//
//        List<PostSouvenirDto> souvenirs = List.of(
//                new PostSouvenirDto("Souvenir1", 10.0,
//                        LocalDateTime.now(),
//                        PostBrandDto
//                                .builder()
//                                .country("C")
//                                .name("n")
//                                .build()),
//                new PostSouvenirDto("Souvenir", 10.0,
//                        LocalDateTime.now(),
//                        PostBrandDto
//                                .builder()
//                                .country("K")
//                                .name("n")
//                                .build())
//        );
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter writer = new PrintWriter(stringWriter);
//        when(response.getWriter()).thenReturn(writer);
//        SouvenirServlet servlet = new SouvenirServlet();
//        servlet.doGet(request, response);
//        verify(req, times(1)).getParameter("page");
//        verify(resp, times(1)).getWriter();
//
//        String result = stringWriter.toString();
//    }
}
