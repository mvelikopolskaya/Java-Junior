import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.UUID;

public class QueryBuilder {

    public String buildInsertQuery(Object ob) {
        Class<?> clazz = ob.getClass();
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        query.append(addTable(clazz));
        query.append("(");
        Field[] fields = clazz.getDeclaredFields();
        query.append(addField(fields));
        query.append(") VALUES (");
        query.append(readField(fields, ob));
        query.append(")");
        return query.toString();
    }

    public String buildUpdateQuery(Object ob) {
        Class<?> clazz = ob.getClass();
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ");
        query.append(addTable(clazz));
        query.append(" SET ");
        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields) {
                field.setAccessible(true);
                Column columnAnnotation = field.getAnnotation(Column.class);
                if(columnAnnotation.primaryKey()){
                    continue;
                }
                try {
                    query.append(columnAnnotation.name()).append(" = '").append(field.get(ob)).append("', ");
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        deleteLastComma(query);
        query.append(" WHERE ");
        for(Field field: fields) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if(columnAnnotation.primaryKey()){
                try {
                    query.append(columnAnnotation.name()).append(" = '").append(field.get(ob)).append("'");
                    break;
                }
                catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return query.toString();
    }

    public String buildSelectQuery(Class<?> clazz, UUID primaryKey) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ");
        query.append(addTable(clazz));
        query.append(" WHERE ");
        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if(columnAnnotation.primaryKey()){
                query.append(columnAnnotation.name()).append(" = ").append(primaryKey);
                break;
            }
        }
        return query.toString();
    }

    public String buildDeleteQuery(Class<?> clazz, UUID primaryKey) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ");
        query.append(addTable(clazz));
        query.append(" WHERE ");
        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if(columnAnnotation.primaryKey()){
                query.append(columnAnnotation.name()).append(" = ").append(primaryKey);
                break;
            }
        }
        return query.toString();
    }

    public static String addTable(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        if (clazz.isAnnotationPresent(Table.class)){
           Table tableAnnotation = clazz.getAnnotation(Table.class);
           sb.append(tableAnnotation.name());
        }
        return sb.toString();
    }

    public static String addField(Field[] fields) {
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)){
                Column columnAnnotation = field.getAnnotation(Column.class);
                sb.append(columnAnnotation.name()).append(", ");
            }
        }
        deleteLastComma(sb);
        return sb.toString();
    }

    public static String readField(Field[] fields, Object obj) {
        StringBuilder sb = new StringBuilder();
        try{
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)){
                    field.setAccessible(true);
                    sb.append("'").append(field.get(obj)).append("', ");
                }
            }
           deleteLastComma(sb);
        } catch (IllegalAccessException e) {
            e.printStackTrace();;
        }
        return sb.toString();
    }

    public static String findPrimaryKey(Field[] fields, Object obj) {
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (field.isAnnotationPresent(Column.class)){
                if(columnAnnotation.primaryKey()) {
                    sb.append(columnAnnotation.name());
                }
            }
        }
        return sb.toString();
    }

    public static String deleteLastComma(StringBuilder sb) {
        if (sb.charAt(sb.length() - 2) == ',') {
            sb.delete(sb.length() - 2, sb.length());
        }
        return sb.toString();
    }
}
