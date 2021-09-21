package com.officeai.triprequest;

import java.io.File;

public class DeleteTest {
    public static void main(String args[]) {
        String home = System.getProperty("user.home");
        File file = new File(home + "/triprequest/triprequest-frontend-master/dist/doc/triprequest.xlsx");
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("ファイルを削除しました");
            } else {
                System.out.println("ファイルの削除に失敗しました");
            }
        } else {
            System.out.println("ファイルが見つかりません");
        }
    }
}


