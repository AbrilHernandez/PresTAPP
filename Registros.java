public class Registros extends ActionBarActivity {

    Vector vectorfull;
    int indiceSelected;
    ListView lista;
    ArrayAdapter adapter;
    DBManager manager;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);
        manager = new DBManager(this);
        crearLista();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ir(position);

            }
        });

  
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }