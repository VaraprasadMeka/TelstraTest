package com.sample.telstratest;

import java.util.List;

/**
 * Created by Vara on 4/13/2015.
 */
public class JsonResponse {

    public String title;

    public List<Row> rows;

    public class Row
    {
        public String title;
        public String description;
        public String imageHref;
    }
}
