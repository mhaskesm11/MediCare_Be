package com.pharma.medicare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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

	@Query
	(value="select * from txn_pdf_data tpd where LOWER(tpd.customer_name) = ?1 and is_active = 'Y' ",
						nativeQuery=true)
	List<PDFFileDetails> getPdfFileByCustomerName(String customerName);

	@Modifying
    @Transactional
	@Query
	(value="UPDATE txn_pdf_data SET is_active='N' where LOWER(customer_name) = ?1",
						nativeQuery=true)
	void deleteAllPdfFilebyCustomerName(String customerName);
	
	

}
