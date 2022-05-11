package com.smart.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entities.Contact;
import com.smart.exceptions.OperationFailedException;

@Service
public class ExcelHelper {
	
	
	public boolean checkExcelFormat(MultipartFile file) {
		String contentType = file .getContentType();
		if(contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			return true;
		}else {
			return false;
		}
	}
	
	public List<Contact> convertExcelToList(MultipartFile file) throws OperationFailedException{
		
		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())){
			List<Contact> contacts = new ArrayList<>();
			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheet("Sheet1");
			if(sheet != null) {
				sheet.forEach((row)->{
					Contact contact = getContact(row);
					contacts.add(contact);
				});
				return contacts;
			}else {
				throw new OperationFailedException("Fail to parse excel file sheet Sheet1 not found");
				}
			
		} catch (IOException e) {
			throw new OperationFailedException("Fail to parse excel"+e.getMessage());
		}
		
	}
	
	public static Contact getContact(Row row) {
		Contact contact = new Contact();
		contact.setcId((int) row.getCell(0).getNumericCellValue());
		contact.setName(row.getCell(1).getStringCellValue());
		contact.setSecondName(row.getCell(2).getStringCellValue());
		contact.setPhone(row.getCell(3).getStringCellValue());
		contact.setEmail(row.getCell(4).getStringCellValue());
		contact.setWork(row.getCell(5).getStringCellValue());
		contact.setDescription(row.getCell(6).getStringCellValue());
		contact.setImage(row.getCell(7).getStringCellValue());		
		return contact;
	}
}
