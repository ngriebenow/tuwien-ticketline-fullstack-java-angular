import { Injectable } from '@angular/core';
import * as jsPDF from 'jspdf';

@Injectable({
  providedIn: 'root'
})
export class PdfService {

  constructor() { }
  createPdf() {
    const pdf = new jsPDF();
    return pdf;
  }
}
