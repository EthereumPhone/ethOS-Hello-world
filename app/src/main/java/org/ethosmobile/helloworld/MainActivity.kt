package org.ethosmobile.helloworld

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.ethereumphone.walletsdk.WalletSDK
import org.ethosmobile.helloworld.ui.theme.EthOSHelloWorldTheme
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Create Web3j object
        val web3j = Web3j.build(HttpService("https://cloudflare-eth.com"))

        // Create WalletSDK using context and previously created Web3j Instance
        val walletSDK = WalletSDK(
            context = this,
            web3jInstance = web3j
        )


        setContent {
            EthOSHelloWorldTheme {
                // CoroutineScope for walletsdk calls
                val coroutineScope = rememberCoroutineScope()

                // Context object to use with Toast
                val currentContext = LocalContext.current

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { // Centers the content within the Box
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally // Centers horizontally
                        ) {
                            Greeting()

                            // Sign Message button
                            Button(onClick = {
                                coroutineScope.launch {
                                    // Use WalletSDK to sign Message "Hello World!"
                                    val signatureResult = walletSDK.signMessage("Hello World!")

                                    // Output signature result as a toast
                                    Toast.makeText(currentContext, "This is the signature: $signatureResult", Toast.LENGTH_LONG).show()
                                }
                            }) {
                                Text(text = "Sign Message")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Text(
        text = "Sign \"Hello World!\" by clicking the button below.",
        modifier = modifier
    )
}