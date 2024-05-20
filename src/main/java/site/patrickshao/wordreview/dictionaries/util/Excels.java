package site.patrickshao.wordreview.dictionaries.util;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import site.patrickshao.wordreview.constants.Constants;
import site.patrickshao.wordreview.dictionaries.entity.basic.Frequency;
import site.patrickshao.wordreview.dictionaries.entity.basic.PartOfSpeech;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

;

public class Excels {
    public static void main(String[] args) throws IOException {
        @Cleanup
        var workbook = new XSSFWorkbook("frequency60000.xlsx");
        var sheet = workbook.getSheetAt(0);

        double total, spoken, fiction, magazine, newspapaer, academic;
        PosWordPair word;
        String en;
        PartOfSpeech pos;
        ArrayList<Info> res = new ArrayList<>();
        for (int i = 1; i < 60024; i++) {
            pos = Texts.toEnum(sheet.getRow(i).getCell(1).getStringCellValue());
            en = sheet.getRow(i).getCell(2).getStringCellValue().replaceAll("[ ()]", "");
            en = en.replaceAll("-", " ");
            word = new PosWordPair(en, pos);
            total = sheet.getRow(i).getCell(3).getNumericCellValue() / 396_106_283;
            spoken = sheet.getRow(i).getCell(4).getNumericCellValue() / 81_917_807;
            fiction = sheet.getRow(i).getCell(5).getNumericCellValue() / 79_496_672;
            magazine = sheet.getRow(i).getCell(6).getNumericCellValue() / 81_785_189;
            newspapaer = sheet.getRow(i).getCell(7).getNumericCellValue() / 75_445_670;
            academic = sheet.getRow(i).getCell(8).getNumericCellValue() / 77_460_945;
            var fre = new Frequency(total, spoken, fiction, magazine, newspapaer, academic);
            res.add(new Info(word, fre));
        }
        new FileOutputStream(Constants.Files.frenquency_json).write(new Gson().toJson(res, ArrayList.class).getBytes());
    }

    @Data@AllArgsConstructor
    public static class Info {
        PosWordPair pair;
        Frequency frequency;
    }

    @Data
    @AllArgsConstructor
    public static class PosWordPair {
        final String en_word;
        final PartOfSpeech pos;
    }


//    public final double total 396,106,283 ;
//    public final double spoken 81,917,807 ;
//    public final double fiction 79,496,672 ;
//    public final double magazine 81,785,189 ;
//    public final double newspapaer 75,445,670 ;
//    public final double academic 77,460,945 ;
}
