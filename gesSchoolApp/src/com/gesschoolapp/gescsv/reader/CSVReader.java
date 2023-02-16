package com.gesschoolapp.gescsv.reader;

import com.gesschoolapp.Exceptions.CSVException;

import java.io.File;
import java.util.List;

public interface CSVReader<T> {

    List<String> readFile(File file) throws CSVException;

    List<String[]> getData(List<String> lines) throws CSVException;

    List<T> csvToObject(List<String[]> data) throws CSVException;
}
