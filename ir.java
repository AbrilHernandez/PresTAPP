private void ir(int pos) {
        //manager.actualizarIndice(pos);
        Intent sig = new Intent(this, Informacion.class);
        sig.putExtra("indice", pos);
        this.startActivity(sig);
        finish();
    }//ir al siguiente activity