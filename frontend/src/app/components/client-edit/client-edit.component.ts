import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder} from '@angular/forms';
import {Client} from '../../dtos/client';
import {ClientService} from '../../services/client.service';
import {AlertService} from '../../services/alert.service';

@Component({
  selector: 'app-client-edit',
  templateUrl: './client-edit.component.html',
  styleUrls: ['./client-edit.component.scss']
})
export class ClientEditComponent implements OnInit {

  public queryParams: Client;

  public searchForm = this.formBuilder.group({
    id: [''],
    name: [''],
    surname: [''],
    email: ['']
  });

  constructor(private router: Router,
              private clientService: ClientService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private alertService: AlertService) {
    this.queryParams = new Client(0, '', '', '');
  }

  ngOnInit() {
    this.clientService.getClientById(this.route.snapshot.paramMap.get('client')).subscribe(
      (client: Client) => {
        this.queryParams = client;
      },
      error => {
        this.alertService.error('Kunde konnte nicht geladen werden!');
      }
    );
  }


  /**
   * Sends client update request
   */
  updateClient() {
    console.log(this.queryParams);
    if (this.queryParams.name === '') {
      this.alertService.warning('Kunden müssen einen Vornamen haben!');
    } else if (this.queryParams.surname === '') {
      this.alertService.warning('Kunden müssen einen Nachnamen haben!');
    } else if (this.queryParams.email === '') {
      this.alertService.warning('Kunden müssen eine Email haben!');
    } else {
      this.clientService.updateClient(this.queryParams).subscribe(
        () => {
          history.back();
        }
      );
    }
  }

}
