package dev.ruben.atp.utils;

import dev.ruben.atp.utils.PaginationLinksUtils;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaginationLinksUtilsTest {

    @Test
    public void testCreateLinkHeader() {
        PaginationLinksUtils paginationLinksUtils = new PaginationLinksUtils();
        Page<Object> page = mock(Page.class);
        List<Object> content = new ArrayList<>();
        when(page.getContent()).thenReturn(content);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("http:/localhost/api/resource");

        int pageNumber = 2;
        int pageSize = 10;
        long totalElements = 30;
        int totalPages = 3;
        when(page.getContent()).thenReturn(content);
        when(page.getNumber()).thenReturn(pageNumber);
        when(page.getSize()).thenReturn(pageSize);
        when(page.getTotalElements()).thenReturn(totalElements);
        when(page.getTotalPages()).thenReturn(totalPages);
        when(page.hasNext()).thenReturn(false);
        when(page.hasPrevious()).thenReturn(false);
        when(page.isFirst()).thenReturn(true);
        when(page.isLast()).thenReturn(true);
        when(page.getNumber()).thenReturn(pageNumber);
        when(page.getSize()).thenReturn(pageSize);
        when(page.getTotalElements()).thenReturn(totalElements);
        when(page.getTotalPages()).thenReturn(totalPages);
        when(page.hasNext()).thenReturn(true);
        when(page.hasPrevious()).thenReturn(true);
        when(page.isFirst()).thenReturn(false);
        when(page.isLast()).thenReturn(false);

        // When
        String linkHeader = paginationLinksUtils.createLinkHeader(page, uriBuilder);

        // Then
        String expectedLinkHeader = "<http:/localhost/api/resource?page=3&size=10>; rel=\"next\", " +
                "<http:/localhost/api/resource?page=1&size=10>; rel=\"prev\", " +
                "<http:/localhost/api/resource?page=0&size=10>; rel=\"first\", " +
                "<http:/localhost/api/resource?page=2&size=10>; rel=\"last\"";
        assertEquals(expectedLinkHeader, linkHeader);
    }
}
