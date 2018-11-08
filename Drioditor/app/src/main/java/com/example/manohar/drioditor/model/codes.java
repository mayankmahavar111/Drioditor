package com.example.manohar.drioditor.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName =  "codes")
public class codes {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "code")
    private String codeText;
    @ColumnInfo(name = "date")
    private Long codeDate;

    @Ignore
    private boolean checked=false;

    public codes() {
    }

    public codes(String codeText, Long codeDate) {
        this.codeText = codeText;
        this.codeDate = codeDate;
    }

    public String getCodeText() {
        return codeText;
    }

    public void setCodeText(String codeText) {
        this.codeText = codeText;
    }

    public Long getCodeDate() {
        return codeDate;
    }

    public void setCodeDate(Long codeDate) {
        this.codeDate = codeDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "codes{" +
                "id=" + id +
                ", codeDate=" + codeDate +
                '}';
    }
}
