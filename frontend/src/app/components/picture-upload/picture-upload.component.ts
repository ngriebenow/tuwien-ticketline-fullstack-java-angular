import {Component, OnDestroy, OnInit} from '@angular/core';
import {PictureService} from '../../services/picture.service';
import {PictureTransferService} from '../../services/picture-transfer.service';
import {AlertService} from '../../services/alert.service';
import {Picture} from '../../dtos/picture';

@Component({
  selector: 'app-picture-upload',
  templateUrl: './picture-upload.component.html',
  styleUrls: ['./picture-upload.component.scss']
})
export class PictureUploadComponent implements OnInit, OnDestroy {
  constructor(private pictureService: PictureService,
              private pictureTransferService: PictureTransferService,
              private alertService: AlertService) {
  }
  ngOnInit() {

    const element = document.querySelector('.droppable');
    function callback(droppedFiles: File[]) {
      for (const file of droppedFiles) {
        if (this.valid(file)) {
          this.appendPictureToTransferService(file);
        }
      }
    }
    this.makedroppable(element, callback.bind(this));
  }
  getPictures(): any[] {
    return this.pictureTransferService.getPictures();
  }
  valid (file: File): boolean {
    if (file.size > 1048576) {
      this.alertService.error('Datei zu groß, Maximalgröße 1 MB');
      return false;
    }
    if (file.type !== 'image/jpeg' && file.type !== 'image/png') {
      this.alertService.error('Falscher Dateityp, nur png oder jpg zulässig');
      return false;
    }
    return true;
  }
  deletePicture(picture: any) {
    this.pictureTransferService.deletePicture(picture);
  }
  appendPictureToTransferService(file: File) {
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      this.pictureTransferService.pushPicture(new Picture(0, file, reader.result)); }, false);
    if (file) {
      reader.readAsDataURL(file);
    }
  }
  ngOnDestroy() {
    this.pictureTransferService.clearData();
  }
  makedroppable(element, callback) {

    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.setAttribute('multiple', 'true');
    input.setAttribute('id', 'fileInput');
    input.style.display = 'none';

    input.addEventListener('change', triggerCallback);
    element.appendChild(input);

    element.addEventListener('mouseover', function(e) {
      element.classList.add('mouseover');
    });

    element.addEventListener('mouseleave', function(e) {
      element.classList.remove('mouseover');
    });

    element.addEventListener('dragover', function(e) {
      e.preventDefault();
      e.stopPropagation();
      element.classList.add('dragover');
    });

    element.addEventListener('dragleave', function(e) {
      e.preventDefault();
      e.stopPropagation();
      element.classList.remove('dragover');
    });

    element.addEventListener('drop', function(e) {
      e.preventDefault();
      e.stopPropagation();
      element.classList.remove('dragover');
      triggerCallback(e);
    });

    element.addEventListener('click', function() {
      input.value = null;
      input.click();
    });

    function triggerCallback(e) {
      let droppedFiles;
      if (e.dataTransfer) {
        droppedFiles = e.dataTransfer.files;
      } else if (e.target) {
        droppedFiles = e.target.files;
      }
      callback.call(null, droppedFiles);
    }
  }

}
