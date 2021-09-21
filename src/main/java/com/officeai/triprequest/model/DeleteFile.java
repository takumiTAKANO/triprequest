package com.officeai.triprequest.model;

import lombok.Data;

import java.io.File;
@Data
public class DeleteFile {
    String fileName1;
    String fileName2;
    public void delete() {
        String home = System.getProperty("user.home");
        String str = "";
        File file1 = new File(home + "/triprequest/triprequest-frontend-master/dist/doc/"+fileName1);
        File file2 = new File(home + "/triprequest/triprequest-frontend-master/dist/doc/"+fileName2);
        if (file1.exists()) {
            if (file1.delete()){
            } else {
                System.out.println("ファイルの削除に失敗しました");
            }
        } else {
            System.out.println("ファイルが見つかりません");
        }
        if (file2.exists()) {
            if (file2.delete()){
            } else {
                System.out.println("ファイルの削除に失敗しました");
            }
        } else {
            System.out.println("ファイルが見つかりません");
        }
    }
    }

