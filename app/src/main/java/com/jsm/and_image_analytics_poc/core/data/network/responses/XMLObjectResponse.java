package com.jsm.and_image_analytics_poc.core.data.network.responses;

import java.util.List;

public interface XMLObjectResponse <E> {
    List<E> getItems();
}
