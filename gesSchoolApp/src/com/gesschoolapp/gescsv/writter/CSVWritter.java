package com.gesschoolapp.gescsv.writter;

import com.gesschoolapp.Exceptions.CSVException;

import java.util.List;

public interface CSVWritter <T> {
    void writeCSVFile(String fileName, List<T> list) throws CSVException;
}
