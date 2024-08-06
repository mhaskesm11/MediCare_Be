package com.pharma.medicare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pharma.medicare.domain.PDFFileDetails;

public interface PDFFileDataRepository extends JpaRepository<PDFFileDetails, Long>{

	@Query
	(value="select tpd.pdf_file_data as pdfFileData from txn_pdf_data tpd where tpd.invoice_number=?1",
					nativeQuery=true)
	byte[]  findByInvoiceNumber(String invoiceNumber);
	
	@Query
	(value="select * from txn_pdf_data tpd where tpd.invoice_number=?1",
						nativeQuery=true)
	Optional<PDFFileDetails>  getPdfFileByInvoiceNumber(String invoiceNumber);
	
	

}
