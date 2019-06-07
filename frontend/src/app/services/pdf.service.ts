import { Injectable } from '@angular/core';
import * as jsPDF from 'jspdf';

@Injectable({
  providedIn: 'root'
})
export class PdfService {
  constructor() {}
  /**
   * Creates a empty pdf
   * Further configurations for all our pdfs can be done here.
   * @return created pdf
   */
  createPdf() {
    const pdf = new jsPDF();
    return pdf;
  }
}
