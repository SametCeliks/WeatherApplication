package com.samet.weatherapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.samet.weatherapplication.api.NetworkResponse
import com.samet.weatherapplication.api.WeatherModel
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import com.samet.weatherapplication.ui.theme.bebasneue


@Composable
fun WeatherPage(viewModel: WeatherViewModel){

    var city by remember {
        mutableStateOf("")
    }


    val weatherResult = viewModel.weatherResult.observeAsState()

    Column (
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {


            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = city,
                onValueChange = {
                    city=it
                }, label = {
                    Text(text = stringResource(id= R.string.Search_for_any_location), fontFamily = bebasneue)
                }
            )



            IconButton(onClick = {
                viewModel.getData(city)
            }){
                Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(id= R.string.Search_for_any_location))
            }


        }

        /*Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Spacer(modifier = Modifier.height(60.dp))


            Image(painter = painterResource(R.drawable.wee), contentDescription = "weatherImage",
                modifier = Modifier.size(200.dp))

        }*/


        when(val result = weatherResult.value){
            is NetworkResponse.Error -> {
                Text(text=result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Succes -> {
                WeatherDetails(data = result.data)
            }
            null -> {}
        }
    }

}

@Composable
fun WeatherDetails(data : WeatherModel) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location icon",
                modifier = Modifier.size(40.dp)
            )
            Text(text = data.location.name, fontSize = 30.sp, fontFamily = bebasneue)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = data.location.country, fontSize = 18.sp, fontFamily = bebasneue, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = " ${data.current.temp_c} ° c",
            fontFamily = bebasneue,
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        AsyncImage(
            modifier = Modifier.size(160.dp),
            model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
            contentDescription = "Condition icon"
        )
        Text(
            text =  data.current.condition.text,
            fontSize = 35.sp,
            fontFamily = bebasneue,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card{
            Column (
                modifier = Modifier.fillMaxWidth()
            ){
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    WeatherKeyValue(stringResource(id=R.string.Humidity),data.current.humidity)
                    WeatherKeyValue(stringResource(id = R.string.Wind_Speed),data.current.wind_kph)

                }
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    WeatherKeyValue(stringResource(id = R.string.UV),data.current.uv)
                    WeatherKeyValue(stringResource(id = R.string.Participation),data.current.precip_mm)

                }
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    WeatherKeyValue(stringResource(id = R.string.Local_Time),data.location.localtime.split(" ")[1])
                    WeatherKeyValue(stringResource(id = R.string.Local_Date),data.location.localtime.split(" ")[0])

                }
            }
        }

    }
}
@Composable
fun WeatherKeyValue(key: String,value:String){
    Column (
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text=value, fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = bebasneue)
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.Gray, fontFamily = bebasneue)
    }
}