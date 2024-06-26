package campus.tech.kakao.contacts

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(
    val contacts: MutableList<Contact>,
    private val mainTextVisibilityUpdater: () -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val displayName: TextView = itemView.findViewById(R.id.display_name)
        val name: TextView = itemView.findViewById(R.id.full_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.displayName.text = contact.name.first().toString()
        holder.name.text = contact.name

        holder.itemView.setOnClickListener {
            val context = it.context
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("phoneNum", contact.phoneNum)
                putExtra("name", contact.name)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = contacts.size

    fun addContact(contact: Contact) {
        contacts.add(contact)
        notifyItemInserted(contacts.size - 1)
        mainTextVisibilityUpdater()
    }
}
