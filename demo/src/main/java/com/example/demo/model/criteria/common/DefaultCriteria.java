package com.example.demo.model.criteria.common;

import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class DefaultCriteria implements Serializable {
    public static final String SORT_DELIMITER = ":";
    public static final String SORT_DESC = "desc";

    @Min(0)
    private int offset = 0;
    @Min(1)
    private int limit = 25;
    private String sort;

    public Sort sortable() {
        Sort s = Sort.unsorted();
        if (StringUtils.hasText(sort)) {
            Set<String> sortParams = StringUtils.commaDelimitedListToSet(sort);
            List<Sort.Order> orders = new ArrayList<>();
            sortParams.forEach(it -> {
                String[] params = StringUtils.split(it, SORT_DELIMITER);
                if (params != null && params.length > 0) {
                    if (SORT_DESC.equalsIgnoreCase(params[1]))
                        orders.add(Sort.Order.desc(params[0]).with(Sort.NullHandling.NULLS_LAST));
                    else
                        orders.add(Sort.Order.asc(params[0]).with(Sort.NullHandling.NULLS_LAST));
                } else
                    orders.add(Sort.Order.asc(it).with(Sort.NullHandling.NULLS_LAST));
            } );
            if (!orders.isEmpty()) {
                s = Sort.by(orders);
            }
        }
        return s;
    }

    /**
     * Return pageable object
     */
    public Pageable pageable() {
        return OffsetPageable.builder().limit(limit).offset(offset).sort(sortable()).build();
    }
}
