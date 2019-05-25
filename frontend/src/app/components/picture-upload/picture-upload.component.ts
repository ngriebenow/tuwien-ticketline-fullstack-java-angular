import { Component, OnInit } from '@angular/core';
import {PictureService} from '../../services/picture.service';
import {PictureTransferService} from '../../services/picture-transfer.service';

@Component({
  selector: 'app-picture-upload',
  templateUrl: './picture-upload.component.html',
  styleUrls: ['./picture-upload.component.scss']
})
export class PictureUploadComponent implements OnInit {

  constructor(private pictureService: PictureService,
              private pictureTransferService: PictureTransferService) { }
  ngOnInit() {
  }
  onFileSelected(event) {
    if (event.target.files[0].size > 1048576) {
      alert('Error, file too big');
    } else {
      this.pictureService.uploadPicture(event.target.files[0])
      .subscribe(res => this.pictureTransferService.appendData(res));
    }
  }
}
