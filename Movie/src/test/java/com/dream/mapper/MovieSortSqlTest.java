package com.dream.mapper;

import com.dream.po.Selectquery;
import java.io.InputStream;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.Configuration;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/** Exercise the real mapper without a database or a running Spring application. */
public class MovieSortSqlTest {
    private Configuration configuration;

    @Before
    public void loadMapper() throws Exception {
        configuration = new Configuration();
        String resource = "com/dream/mapper/MovieMapper.xml";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(resource);
        assertNotNull("Mapper must be present on the test classpath", stream);
        try {
            new XMLMapperBuilder(stream, configuration, resource,
                    configuration.getSqlFragments()).parse();
        } finally {
            stream.close();
        }
    }

    private void assertSort(int category, String input, String column) {
        Selectquery query = new Selectquery();
        query.setCategoryid(category);
        query.setmolimit(20);
        query.setSort(input);
        BoundSql bound = configuration.getMappedStatement(
                "com.dream.mapper.MovieMapper.selectBycategory").getBoundSql(query);
        String sql = bound.getSql().replaceAll("\\s+", " ").trim();
        String expected = category == 0 ? "select * from movie" :
                "select * from movie m, moviecategory mc where m.movieid=mc.movieid and mc.categoryid = ?";
        assertEquals(expected + " order by " + column + " desc LIMIT ?,20;", sql);
        assertEquals(category == 0 ? 1 : 2, bound.getParameterMappings().size());
        assertEquals("molimit", bound.getParameterMappings().get(
                bound.getParameterMappings().size() - 1).getProperty());
    }

    @Test
    public void preservesAllUiSortOptionsForBothQueries() {
        for (int category : new int[]{0, 7}) {
            for (String sort : new String[]{"numrating", "showyear", "averating"}) {
                assertSort(category, sort, sort);
            }
        }
    }

    @Test
    public void unknownAndInjectionInputsFallBackToPopularity() {
        for (int category : new int[]{0, 7}) {
            for (String sort : new String[]{null, "", "unknown", "showyear desc",
                    "numrating; DROP TABLE movie; --", "(SELECT SLEEP(5))", "averating/*comment*/"}) {
                assertSort(category, sort, "numrating");
            }
        }
    }
}
