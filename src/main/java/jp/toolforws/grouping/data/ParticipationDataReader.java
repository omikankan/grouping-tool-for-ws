package jp.toolforws.grouping.data;

import jp.toolforws.grouping.Participant;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author omikankan
 */
public class ParticipationDataReader {

    // データがあるシートが何シート目か(0始まり)
    private static final int DATA_SHEET_NUMBER = 0;
    // 最後のデータはこの行より前に入れること(0始まり)
    private static final int MAX_ROW = 1000;
    private static final int START_ROW = 1;

    // 必要なデータがある列(0始まり)
    private static final int DATA_COLUMN_ID = 0;
    private static final int DATA_COLUMN_NAME = 1;
    private static final int DATA_COLUMN_ATTR01 = 2;
    private static final int DATA_COLUMN_ATTR02 = 3;
    private static final int DATA_COLUMN_ATTR03 = 4;
    private static final int DATA_COLUMN_LAST_GROUP1 = 5;
    private static final int DATA_COLUMN_LAST_GROUP2 = 6;
    private static final int DATA_COLUMN_LAST_GROUP3 = 7;
    private static final int DATA_COLUMN_GROUP1 = 8;
    private static final int DATA_COLUMN_GROUP2 = 9;
    private static final int DATA_COLUMN_GROUP3 = 10;

    private final String dataPath;

    public ParticipationDataReader(String dataPath){
        this.dataPath = dataPath;
    }

    public List<Participant> read() throws FileNotFoundException {
        File file = new File(dataPath);
        Workbook wb = null;

        if(!(file.exists())){
            System.out.println(" [ERROR] The data file is not exist. The path=" + dataPath);
             throw new FileNotFoundException();
        }

        try {
            wb = WorkbookFactory.create(file);
        } catch (IOException e) {
            System.out.println(" [ERROR] Failed to read the data file. ");
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            System.out.println(" [ERROR] The data file format is invalid. ");
            e.printStackTrace();
        } finally {
            try {
                wb.close();
            } catch (IOException e) {
                // クローズに失敗したら何もしない
            }
        }

        Sheet sheet = wb.getSheetAt(DATA_SHEET_NUMBER);

        ArrayList<Participant> participantData = new ArrayList<>();

        for(int rowIndex = START_ROW; rowIndex < MAX_ROW; rowIndex++){
            Row row = sheet.getRow(rowIndex);
            // 1列目（ID列）に値がないセルがあったら以降は読まない
            if(row != null && !(row.getCell(0).toString().equals(""))){
                participantData.add(createParticipant(row));
            }else{
                break;
            }
        }
        return participantData;
    }

    private Participant createParticipant(Row row){
        Participant participant = new Participant();
        // ID 列に値がある行をデータ行とみなす
        if(row != null && !(readCell(row.getCell(DATA_COLUMN_ID)).equals(""))){

            participant.setId(Integer.parseInt(readCell(row.getCell(DATA_COLUMN_ID))));
            participant.setName(readCell(row.getCell(DATA_COLUMN_NAME)));

            participant.setAttribute("attr01", readCell(row.getCell(DATA_COLUMN_ATTR01)));
            participant.setAttribute("attr02", readCell(row.getCell(DATA_COLUMN_ATTR02)));
            participant.setAttribute("attr03", readCell(row.getCell(DATA_COLUMN_ATTR03)));
            participant.setAttribute("lastGroup1", readCell(row.getCell(DATA_COLUMN_LAST_GROUP1)));
            participant.setAttribute("lastGroup2", readCell(row.getCell(DATA_COLUMN_LAST_GROUP2)));
            participant.setAttribute("lastGroup3", readCell(row.getCell(DATA_COLUMN_LAST_GROUP3)));
            participant.setAttribute("group1", readCell(row.getCell(DATA_COLUMN_GROUP1)));
            participant.setAttribute("group2", readCell(row.getCell(DATA_COLUMN_GROUP2)));
            participant.setAttribute("group3", readCell(row.getCell(DATA_COLUMN_GROUP3)));
        }
        return participant;
    }

    private static String readCell(Cell cell){
        String value = "";
        if(cell != null){
            try{
                value =  cell.getStringCellValue();
            }catch(IllegalStateException ex){
                try{
                    // 小数は想定しない
                    value =  Integer.toString((int)cell.getNumericCellValue());
                }catch(Exception ex2){
                    // 読めないセルは値なしとする
                    value = "";
                }
            }
        }
        return value;
    }

}
