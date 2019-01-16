import { by, element } from 'protractor'
import { async, ComponentFixture, TestBed } from '@angular/core/testing'

import { NavigationComponent } from './navigation.component'

describe('NavigationComponent', () => {
  let component: NavigationComponent
  let fixture: ComponentFixture<NavigationComponent>

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [NavigationComponent]
    }).compileComponents()
  }))

  beforeEach(() => {
    fixture = TestBed.createComponent(NavigationComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })

  it('should render 3 links', () => {
    const fixture = TestBed.createComponent(NavigationComponent)
    fixture.detectChanges()
    const compiled = fixture.debugElement.nativeElement
    expect(compiled.querySelectorAll('a').length).toBe(3)
  })
})
