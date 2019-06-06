import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {ClientFilter} from '../../dtos/client-filter';
import {Client} from '../../dtos/client';
import {ClientService} from '../../services/client.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-user-filter',
  templateUrl: './client-filter.component.html',
  styleUrls: ['./client-filter.component.scss']
})
export class ClientFilterComponent implements OnInit {

  public clients: Client[] = [];
  public page = 0;
  public count = 20;
  public queryParams: ClientFilter;
  public customerID: string;
  public edit: boolean;

  public searchForm = this.formBuilder.group({
    clientName: [''],
    clientSurname: [''],
    clientEmail: [''],
    id: [''],
  });

  constructor(private clientService: ClientService, private formBuilder: FormBuilder,
              private router: Router, private route: ActivatedRoute) {
    this.edit = false;
    if (this.route.snapshot.paramMap.get('edit') === 'edit') {
      this.edit = true;
    }
    this.queryParams = new ClientFilter('', '', '', 0, 10);
  }

  ngOnInit() {
    this.loadClients();
  }

  public loadClients(): void {
    this.queryParams.page = this.page;
    this.queryParams.count = this.count;
    this.clientService.getClientsFiltered(this.queryParams).subscribe(
      (client: Client[]) => {
        this.clients = client;
      },
      error => {
      }
    );
  }

  public loadClient(): void {
    this.clientService.getClientById(this.customerID).subscribe(
      (client) => {
        this.clients = new Array();
        this.clients.push(client);
      },
      error => {
      }
    );
  }

  public nextPage(): void {
    this.page++;
    this.loadClients();
  }

  public previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadClients();
    }
  }

  public update(): void {
    if (this.customerID === '') {
      this.loadClients();
    } else {
      this.loadClient();
    }

  }

  selectUser(client: Client): void {
    localStorage.setItem('client', JSON.stringify(client));
    if (this.edit) {
      this.router.navigate(['/client-edit/' + String(client.id)]);
    } else {
      this.router.navigate(['/finalize-transaction']);
    }
  }

  anonym(): void {
    this.clientService.getClientById('0').subscribe(
      (c) => {
        this.selectUser(c);
      },
      error => {
      }
    );
  }

  public addClient(): void {
    this.router.navigate(['/client-add']);
  }

}
