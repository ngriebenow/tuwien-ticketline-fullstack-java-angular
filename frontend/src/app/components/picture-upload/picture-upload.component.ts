import {Component, OnDestroy, OnInit} from '@angular/core';
import {PictureService} from '../../services/picture.service';
import {PictureTransferService} from '../../services/picture-transfer.service';

@Component({
  selector: 'app-picture-upload',
  templateUrl: './picture-upload.component.html',
  styleUrls: ['./picture-upload.component.scss']
})
export class PictureUploadComponent implements OnInit, OnDestroy {

  public files: File[];
  constructor(private pictureService: PictureService,
              private pictureTransferService: PictureTransferService) {this.files = new Array();
  }
  ngOnInit() {

    const element = document.querySelector('.droppable');
    function callback(droppedFiles) {
      for (const file of droppedFiles) {
        // alert(file.name);
        this.uploadFile(file);
      }
    }
    this.makedroppable(element, callback.bind(this));
  }

  ngOnDestroy() {
    this.pictureTransferService.clearData();
  }
  /**
   * Gets triggered on file selection and posts the picture data.
   * @param event for file input field
   */
  onFileSelected(event) {
    this.uploadFile(event.target.files[0]);
  }
  uploadFile(file: File) {
    if (file.size > 1048576) {
      alert('Error, file too big');
    } else {
      this.files.push(file);
      this.pictureService.uploadPicture(file)
      .subscribe(res => this.pictureTransferService.appendData(res));
    }
  }

  makedroppable(element, callback) {

    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.setAttribute('multiple', 'true');
    input.setAttribute('id', 'fileInput');
    input.style.display = 'none';

    input.addEventListener('change', triggerCallback);
    element.appendChild(input);

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
