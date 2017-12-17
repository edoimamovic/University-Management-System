import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardStudComponent } from './dashboard-stud.component';

describe('DashboardStudComponent', () => {
  let component: DashboardStudComponent;
  let fixture: ComponentFixture<DashboardStudComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashboardStudComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardStudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
