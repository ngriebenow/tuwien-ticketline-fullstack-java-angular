import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder} from '@angular/forms';
import {Client} from '../../dtos/client';
import {ClientService} from '../../services/client.service';
import {AlertService} from '../../services/alert.service';

@Component({
  selector: 'app-client-add',
  templateUrl: './client-add.component.html',
  styleUrls: ['./client-add.component.scss']
})
export class ClientAddComponent implements OnInit {

  public queryParams: Client;

  public searchForm = this.formBuilder.group({
    name: [''],
    surname: [''],
    email: ['']
  });

  constructor(private router: Router,
              private clientService: ClientService,
              private formBuilder: FormBuilder,
              private alertService: AlertService) {
  }

  ngOnInit() {
    this.queryParams = new Client(null, '', '', '');
  }


  /**
   * Sends client creation request
   */
  createClient() {
    if (this.queryParams.name === '') {
      this.alertService.warning('Kunden müssen einen Vornamen haben!');
    } else if (this.queryParams.surname === '') {
      this.alertService.warning('Kunden müssen einen Nachnamen haben!');
    } else if (this.queryParams.email === '') {
      this.alertService.warning('Kunden müssen eine Email haben!');
    } else {
      this.clientService.createClient(this.queryParams).subscribe(
        () => {
          history.back();
        }
      );
    }
  }
}
