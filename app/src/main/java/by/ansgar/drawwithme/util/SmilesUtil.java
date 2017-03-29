package by.ansgar.drawwithme.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;

/**
 * Created by kirila on 29.3.17.
 */

public class SmilesUtil {

    public static final String SMILE_PATTERN = "(\\[)(.*?)(\\])";

    private static final String SMILES_FOLDER = "smiles";

    public static List<String> getSmiles(Context context) {
        List<String> smiles = null;
        try {
            smiles = new ArrayList<>(Arrays.asList(context.getAssets().list(SMILES_FOLDER)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return smiles;
    }

}
