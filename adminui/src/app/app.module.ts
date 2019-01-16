import { BrowserModule } from '@angular/platform-browser'
import { NgModule } from '@angular/core'
import { ReactiveFormsModule, FormsModule } from '@angular/forms'

import { AppRoutingModule } from './app-routing.module'
import { AppComponent } from './app.component'
import { NavigationComponent } from './navigation/navigation.component'
import { NotImplementedComponent } from './not-implemented/not-implemented.component'
import { SensorsComponent } from './sensors/sensors.component'
import { SensorComponent } from './sensor/sensor.component'
import { HttpClientModule } from '@angular/common/http'

@NgModule({
  declarations: [AppComponent, NavigationComponent, NotImplementedComponent, SensorsComponent, SensorComponent],
  imports: [BrowserModule, FormsModule, ReactiveFormsModule, AppRoutingModule, HttpClientModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
