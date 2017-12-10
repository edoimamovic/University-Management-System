import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardSsluzbaComponent } from './dashboard-ssluzba.component';

describe('DashboardSsluzbaComponent', () => {
  let component: DashboardSsluzbaComponent;
  let fixture: ComponentFixture<DashboardSsluzbaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashboardSsluzbaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardSsluzbaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
