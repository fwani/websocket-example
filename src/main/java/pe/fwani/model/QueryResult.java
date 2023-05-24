package pe.fwani.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QueryResult {
    private String jobId;
    private List<Field> fields;
    private List<Object> records;
}
