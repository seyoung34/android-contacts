package campus.tech.kakao.contacts

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var addContact: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var mainText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainText = findViewById(R.id.mainText)
        addContact = findViewById(R.id.add_contact)
        recyclerView = findViewById(R.id.recyclerView)
        contactAdapter = ContactAdapter(mutableListOf(), this::updateMainTextVisibility)

        recyclerView.adapter = contactAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        addContact.setOnClickListener {
            val intent = Intent(this@MainActivity, AddContact::class.java)
            startActivityForResult(intent, ADD_CONTACT_REQUEST_CODE)
        }

        updateMainTextVisibility()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_CONTACT_REQUEST_CODE && resultCode == RESULT_OK) {
            val name = data?.getStringExtra("name")
            val phoneNum = data?.getStringExtra("phoneNum")

            if (name != null && phoneNum != null) {
                val contact = Contact(name, phoneNum)
                contactAdapter.addContact(contact)
            }
        }
    }

    private fun updateMainTextVisibility() {
        if (contactAdapter.itemCount >= 4) {
            mainText.visibility = View.GONE
        } else {
            mainText.visibility = View.VISIBLE
        }
    }

    companion object {
        const val ADD_CONTACT_REQUEST_CODE = 1
    }
}
