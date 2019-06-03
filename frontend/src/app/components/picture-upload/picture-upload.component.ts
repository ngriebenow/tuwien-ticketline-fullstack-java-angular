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
  /**
   * Create the drag and drop container.
   */
  ngOnInit() {

    const element = document.querySelector('.droppable');
    /**
     * Append all valid dropped files to picture transfer service.
     * @param droppedFiles the files a user dropped in
     */
    function callback(droppedFiles: File[]) {
      for (const file of droppedFiles) {
        if (this.valid(file)) {
          this.appendPictureToTransferService(file);
        }
      }
    }
    this.makedroppable(element, callback.bind(this));
  }
  /**
   * Return all pictures from transfer service for rendering.
   * @return pictures
   */
  getPictures(): any[] {
    return this.pictureTransferService.getPictures();
  }

  /**
   * Check if a dropped file is valid (size and type).
   * @param file which has been dropped
   * @return valid or not valid
   */
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
  /**
   * Delete a dropped but not yet uploaded file.
   * @param picture to be deleted
   */
  deletePicture(picture: Picture) {
    this.pictureTransferService.deletePicture(picture);
  }
  /**
   * Pack a file into a Picture and append it to the transfer service.
   * @param file to be appended
   */
  appendPictureToTransferService(file: File) {
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      this.pictureTransferService.pushPicture(new Picture(0, file, reader.result)); }, false);
    if (file) {
      reader.readAsDataURL(file);
    }
  }
  /**
   * On destroy, clear data from transfer service.
   */
  ngOnDestroy() {
    this.pictureTransferService.clearData();
  }
  /**
   * Turn a div element into a drag and drop container.
   * @param element the div to be modified.
   * @param callback function to be called when files are dropped in.
   */
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
    /**
     * Get dropped files from event and call callback function.
     * @param e event
     */
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
